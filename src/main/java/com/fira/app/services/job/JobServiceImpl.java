package com.fira.app.services.job;

import com.fira.app.entities.*;
import com.fira.app.enums.NotificationType;
import com.fira.app.repository.AccountRepository;
import com.fira.app.repository.JobRepository;
import com.fira.app.repository.NotificationRepository;
import com.fira.app.repository.TaskLabelRepository;
import com.fira.app.requests.job.CreateJobRequest;
import com.fira.app.requests.job.CreateNewJobLabelRequest;
import com.fira.app.requests.job.UpdateJobRequest;
import com.fira.app.utils.BeanHelper;
import com.fira.app.utils.ResponseHelper;
import com.fira.app.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final TaskLabelRepository taskLabelRepository;

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public ResponseEntity<?> store(CreateJobRequest createJobRequest) throws Exception {
        Job job = new Job();
        BeanUtils.copyProperties(createJobRequest, job, BeanHelper.getNullPropertyNames(createJobRequest));
        for (String id : createJobRequest.getMemberIds()) {
            accountRepository.findById(id).ifPresent(job::addMember);
        }
        job.getTaskLabels().addAll(taskLabelRepository.saveAll(createNewLabelList(createJobRequest.getLabels())));
        // get current account;
        Account manager = SecurityHelper.getAccountFromLogged(accountRepository);
        if (manager == null) {
            return ResponseHelper.accessDenied("Access deined");
        }
        job.addMember(manager);
        job.setManager(manager);
        // push all notification to user
        return createAndPushNotification(job, "Bạn vừa có một công việc mới.");
    }

    @Override
    public ResponseEntity<?> update(UpdateJobRequest updateJobRequest) throws Exception {
        Job job = jobRepository.findById(updateJobRequest.getJobId()).orElse(null);
        if (job == null) {
            return ResponseHelper.notFound("Job not found!");
        }
        if (job.getContributesHistories().size() != 0) {
            if (updateJobRequest.getTimeStart() != null) {
                return ResponseHelper.badRequest("Job has history contributes cannot change time start");
            }
        }
        BeanUtils.copyProperties(updateJobRequest, job, BeanHelper.getNullPropertyNames(updateJobRequest));
        // handle member
        switch (updateJobRequest.getMemberAction()) {
            case ADD -> updateJobRequest.getListMemberIds().forEach(m -> {
                accountRepository.findById(m).ifPresent(member -> job.addMember(accountRepository.findById(m).orElse(null)));
            });
            case RESET -> {
                job.getMembers().clear();
                updateJobRequest.getListMemberIds().forEach(m -> {
                    accountRepository.findById(m).ifPresent(member -> job.addMember(accountRepository.findById(m).orElse(null)));
                });
            }
            case REMOVE -> {
                updateJobRequest.getListMemberIds().forEach(m -> {
                    accountRepository.findById(m).ifPresent(member -> job.removeMember(accountRepository.findById(m).orElse(null)));
                });
            }
        }
        switch (updateJobRequest.getLabelAction()) {
            case ADD -> {
                List<TaskLabel> taskLabels = createNewLabelList(updateJobRequest.getLabels());
                job.getTaskLabels().addAll(taskLabelRepository.saveAll(taskLabels));
            }
            case REST -> {
                List<TaskLabel> taskLabels = createNewLabelList(updateJobRequest.getLabels());
                job.getMembers().clear();
                job.getTaskLabels().addAll(taskLabelRepository.saveAll(taskLabels));
            }
            case REMOVE -> {
                updateJobRequest.getRemoveLabelIds().forEach(s -> {
                    job.getTaskLabels().remove(taskLabelRepository.findById(s).orElse(null));
                });
            }
        }
        return ResponseHelper.success(jobRepository.save(job));
    }

    private List<TaskLabel> createNewLabelList(Set<CreateNewJobLabelRequest> createJobRequests) {
        List<TaskLabel> taskLabels = new ArrayList<>();
        for (CreateNewJobLabelRequest labelRequest : createJobRequests) {
            taskLabels.add(createNewLabel(labelRequest));
        }
        return taskLabels;
    }

    private TaskLabel createNewLabel(CreateNewJobLabelRequest createNewJobTableRequest) {
        TaskLabel taskLabel = new TaskLabel();
        taskLabel.setLabelName(createNewJobTableRequest.getLabelName());
        taskLabel.setType(createNewJobTableRequest.getLabelType());
        taskLabel.setLabelColor(createNewJobTableRequest.getLabelColor());
        return taskLabel;
    }

    @Override
    public ResponseEntity<?> destroy(String s) throws Exception {
        Job job = jobRepository.findById(s).orElse(null);
        if (job == null) return ResponseHelper.notFound("Job not found");
        job.getMembers().clear();
        job.getTaskRows().clear();
        job.getContributesHistories().clear();
        job.getTaskLabels().clear();
        jobRepository.save(job);
        jobRepository.deleteById(s);
        return ResponseHelper.success();
    }

    @Override
    public ResponseEntity<?> destroyAll(Set<String> strings) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable, String fieldSort, String sortDesc, String query) throws Exception {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDesc), fieldSort);
        Pageable pageOf = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        if (!Objects.equals(query, "")) {
            return ResponseHelper.success(jobRepository.findAllByJobNameContaining(pageOf, query));
        }
        return ResponseHelper.success(jobRepository.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> createAndPushNotification(List<Job> jobs, String content) {
        return null;
    }

    @Override
    public ResponseEntity<?> getByUser(Pageable pageable, String sortBy, String sortDir, String query) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageOf = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Account currentMember = SecurityHelper.getAccountFromLogged(accountRepository);
        if (!Objects.equals(query, "")) {
            return ResponseHelper.success(jobRepository.findAllByJobNameContainingAndMembers(pageOf, query, currentMember));
        }
        return ResponseHelper.success(jobRepository.findAllByMembers(pageable, currentMember));
    }

    public ResponseEntity<?> createAndPushNotification(Job job, String content) throws Exception {
        Job savedJob = jobRepository.save(job);
        List<Notification> notifications = new ArrayList<>();
        for (Account member : savedJob.getMembers()) {
            Notification notification = new Notification();
            notification.setNotificationType(NotificationType.JOB);
            notification.setData(savedJob.getId());
            notification.setContent(content);
            notification.setToUser(member.getUsername());
            notification.setFromUser(savedJob.getManager().getEmail());
            notifications.add(notification);
        }
        notifications = notificationRepository.saveAll(notifications);
        for (Notification notification : notifications) {
            simpMessagingTemplate.convertAndSendToUser(notification.getToUser(), "/queue/notifications", notification);
        }
        return ResponseHelper.success();
    }
}
