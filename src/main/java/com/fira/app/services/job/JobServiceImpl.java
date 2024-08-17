package com.fira.app.services.job;

import com.fira.app.entities.*;
import com.fira.app.enums.JobStatus;
import com.fira.app.enums.NotificationType;
import com.fira.app.enums.Role;
import com.fira.app.repository.*;
import com.fira.app.requests.job.*;
import com.fira.app.utils.BeanHelper;
import com.fira.app.utils.ResponseHelper;
import com.fira.app.utils.SecurityHelper;
import com.fira.app.utils.SocketPushingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JobServiceImpl implements JobService {
    private final AccountRepository accountRepository;

    private final UserJobRepository userJobRepository;
    private final JobDetailRepository jobDetailRepository;

    private final NotificationRepository notificationRepository;
    private final SocketPushingUtils socketPushingUtils;
    private final JobRepository jobRepository;

    @Override
    public ResponseEntity<?> store(CreateJobRequest createJobRequest) throws Exception {
        Account user = accountRepository.findById(createJobRequest.getUserCreateJobId()).orElse(null);
        if (user == null) {
            return ResponseHelper.badRequest("User creation not found!");
        }
        if (createJobRequest.getStaffsGotJobId().size() <= 0) {
            return ResponseHelper.badRequest("Need more staff got job to create");
        }
        Job job = new Job();
        BeanUtils.copyProperties(createJobRequest, job, BeanHelper.getNullPropertyNames(createJobRequest));
        if (createJobRequest.getStaffsGotJobId() != null) {
            for (String sId : createJobRequest.getStaffsGotJobId()) {
                Account staff = accountRepository.findById(sId).orElse(null);
                if (staff != null) {
                    if (!staff.getId().equals(user.getId())) {
                        job.addStaffToJob(staff, userJobRepository);
                    }
                }
            }
        }
        job.setManager(user);
        JobDetail jobDetail = new JobDetail();
        jobDetail.setDescription(createJobRequest.getDescription());
        jobDetail.setNote(createJobRequest.getNote());
        jobDetail.setTarget(createJobRequest.getTarget());
        jobDetail.setAdditionInfo(createJobRequest.getAdditionInfo());
        jobDetail.setTimeStart(createJobRequest.getTimeStart());
        jobDetail.setTimeEnd(createJobRequest.getTimeEnd());
        job.setJobDetail(jobDetailRepository.save(jobDetail));
        return pushNotificationForStaff(job, "Bạn vừa được giao một công việc");
    }

    private ResponseEntity pushNotificationForStaff(Job job, String content) {
        List<Notification> notifications = new ArrayList<>();
        for (UserJob userJob : job.getUserJobs()) {
            Notification notification = new Notification();
            notification.setFromUser(job.getManager().getId());
            notification.setContent(content);
            notification.setData(job.getId());
            notification.setToUser(userJob.getUser().getId());
            notification.setNotificationType(NotificationType.JOB);
            notifications.add(notification);
        }
        notificationRepository.saveAll(notifications);
        socketPushingUtils.pushNotification(notifications.get(0));
        return ResponseHelper.success();
    }

    @Override
    public ResponseEntity<?> update(UpdateJobRequest updateJobRequest) throws Exception {
        Job job = jobRepository.findById(updateJobRequest.getJobId()).orElseThrow(() -> new Exception("Job not found"));
        BeanUtils.copyProperties(updateJobRequest, job, BeanHelper.getNullPropertyNames(updateJobRequest));
        JobDetail jobDetail = job.getJobDetail();
        BeanUtils.copyProperties(updateJobRequest, jobDetail, BeanHelper.getNullPropertyNames(updateJobRequest));
        job.setJobDetail(jobDetailRepository.save(jobDetail));
        return ResponseHelper.success(jobRepository.save(job));

    }

    @Override
    public ResponseEntity<?> destroy(String s) throws Exception {
        // delete job
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
            return ResponseHelper.success(jobRepository.findByTitleContaining(pageOf, query));
        }
        return ResponseHelper.success(jobRepository.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> giveJobForUser(String jobId, GiveJobForUserRequest request) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new Exception("Job not found"));
        if (!SecurityHelper.getLoggedUser().equals(job.getManager().getUsername())) {
            return ResponseHelper.accessDenied("U do not permission!");
        }
        for (String sId : request.getUserIds()) {
            if (!sId.equals(job.getManager().getId())) {
                Account user = accountRepository.findById(sId).orElse(null);
                if (user != null) {
                    boolean hasExit = false;
                    for (UserJob userJob : job.getUserJobs()) {
                        if (userJob.getUser().getId().equals(sId)) {
                            hasExit = true;
                        }
                    }
                    if (!hasExit) {
                        job.addStaffToJob(user, userJobRepository);
                        pushNotificationForStaff(job, "Bạn vừa được giao một công việc");
                    }
                }

            }
        }
        return ResponseHelper.success(jobRepository.save(job));
    }

    @Override
    public ResponseEntity<?> evoluteJob(String jobId, EvoluteJobRequest request) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new Exception("Job not found"));
        Account currentUser = SecurityHelper.getAccountFromLogged(accountRepository);
        if (currentUser == null) {
            return ResponseHelper.accessDenied("You do not permission");
        }
        if (request.getJobEvaluate() != null) {
            if (!Objects.equals(currentUser.getRole().getName(), Role.ADMIN.toString())) {
                if (!Objects.equals(currentUser.getRole().getName(), Role.MANAGER.toString())) {
                    return ResponseHelper.accessDenied("You do not permission");
                }
                if (!job.getManager().getId().equals(currentUser.getId())) {
                    return ResponseHelper.accessDenied("You do not permission");
                }
            }
            // push notification
            job.getUserJobs().forEach((j) -> {
                if (j.getUser().getId().equals(request.getUserId())) {
                    j.setJobEvaluate(request.getJobEvaluate());
                    userJobRepository.save(j);
                    try {
                        pushNotificationForStaff(job, "Có một công việc đã được đánh giá bởi manager");
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            });
            jobRepository.save(job);
        }
        return ResponseHelper.success();
    }

    @Override
    public ResponseEntity<?> updateUserJobDetailRequest(String jobId, UpdateUserJobDetailRequest request) throws Exception {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            return ResponseHelper.notFound("Job not found");
        }
        for (UserJob userJob : job.getUserJobs()) {
            if (userJob.getUser().getId().equals(request.getUserId())) {
                int oldProgress = userJob.getProgress();
                BeanUtils.copyProperties(request, userJob);
                userJob.setProgress(oldProgress);
                userJob.setCachedProgress(request.getProgress());
                if (request.getStatus() == JobStatus.DONE) {
                    // push notification
                    userJob.setStatus(JobStatus.DONE);
                    // luu lich su cong diem
//
//                    KpiHistory kpiHistory = new KpiHistory();
//                    kpiHistory.setUser(userJob.getUser());
//                    kpiHistoryService.createNewFromOtherService("Cộng " + job.getPointPerJob() + " điểm từ job : " + job.getId(), userJob.getUser());
//                    pushNotificationForAStaff(jobId, userJob.getUser(), job.getManager(), "Bạn vừa hoàn thành một công việc");
                }
                userJobRepository.save(userJob);
            }
            jobRepository.save(job);
        }
        return ResponseHelper.success();
    }

    @Override
    public ResponseEntity<?> getById(String s) throws Exception {
        return ResponseHelper.success(jobRepository.findById(s));
    }
}
