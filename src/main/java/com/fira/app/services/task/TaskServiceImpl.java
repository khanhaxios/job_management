package com.fira.app.services.task;

import com.fira.app.entities.*;
import com.fira.app.repository.*;
import com.fira.app.requests.task.*;
import com.fira.app.utils.BeanHelper;
import com.fira.app.utils.ResponseHelper;
import com.fira.app.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskLabelRepository taskLabelRepository;
    private final TaskRepository taskRepository;
    private final TaskRowRepository taskRowRepository;

    private final AttachmentRepository attachmentRepository;

    private final TaskStepRepository taskStepRepository;

    private final AccountRepository accountRepository;

    private final TaskCommentRepository taskCommentRepository;

    @Override
    public ResponseEntity<?> store(CreateNewTaskRequest createNewTaskRequest) throws Exception {
        // find task row
        TaskRow taskRow = taskRowRepository.findById(createNewTaskRequest.getTaskRowId()).orElse(null);
        if (taskRow == null) {
            return ResponseHelper.notFound("Task row not found");
        }
        if (createNewTaskRequest.getTimeStart().isAfter(createNewTaskRequest.getTimeEnd())) {
            return ResponseHelper.badRequest("Time start must be before time end");
        }

        Task task = new Task();
        task.setTaskDescription(createNewTaskRequest.getTaskDescription());
        task.setTaskName(createNewTaskRequest.getTaskName());
        task.setTaskLabel(taskLabelRepository.findById(createNewTaskRequest.getTaskLabel()).orElse(null));
        task.setTaskProgress(createNewTaskRequest.getTaskProgress());
        task.setTimeStart(createNewTaskRequest.getTimeStart());
        task.setTimeEnd(createNewTaskRequest.getTimeEnd());
        // handle attachment
        List<Attachment> attachments = new ArrayList<>();
        List<TaskStep> steps = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        for (CreateTaskAttachmentRequest attachment : createNewTaskRequest.getAttachments()) {
            Attachment newAttach = new Attachment();
            newAttach.setPath(attachment.getPath());
            attachments.add(newAttach);
        }

        for (CreateNewTaskStepRequest step : createNewTaskRequest.getSteps()) {
            TaskStep newStep = new TaskStep();
            newStep.setStepOrder(step.getStepOrder());
            newStep.setTitle(step.getTitle());
            newStep.setDescription(step.getDescription());
            newStep.setTimeStart(step.getTimeStart());
            newStep.setTimeEnd(step.getTimeEnd());
            steps.add(newStep);
        }
        for (String assignmentsId : createNewTaskRequest.getAssignmentsIds()) {
            Account account = accountRepository.findById(assignmentsId).orElse(null);
            accounts.add(account);
        }

        task.setSteps(new HashSet<>(taskStepRepository.saveAll(steps)));
        task.setAttachments(new HashSet<>(attachmentRepository.saveAll(attachments)));
        task.setAssignments(new HashSet<>(accounts));
        Task savedTask = taskRepository.save(task);
        taskRow.addTask(savedTask);
        return ResponseHelper.success(taskRowRepository.save(taskRow));
    }

    @Override
    public ResponseEntity<?> update(UpdateTaskRequest updateTaskRequest) throws Exception {
        Task task = taskRepository.findById(updateTaskRequest.getId()).orElse(null);
        if (task == null) {
            return ResponseHelper.notFound("Task not found");
        }
        BeanUtils.copyProperties(updateTaskRequest, task, BeanHelper.getNullPropertyNames(updateTaskRequest));
        if (updateTaskRequest.getTaskLabelId() != null) {
            TaskLabel taskLabel = taskLabelRepository.findById(updateTaskRequest.getTaskLabelId()).orElse(null);
            if (taskLabel == null) {
                return ResponseHelper.notFound("Task label not found");
            }
            task.setTaskLabel(taskLabel);
        }
        return ResponseHelper.success(taskRepository.save(task));
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> destroyAll(Set<Long> longs) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable, String fieldSort, String sortDesc, String query) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> updateTaskAssignment(Long taskId, UpdateTaskAssignment updateTaskAssignment) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseHelper.notFound("Task not found");
        }
        List<Account> accounts = accountRepository.findAllById(updateTaskAssignment.getAssginmentIds());

        switch (updateTaskAssignment.getCollectionAction()) {
            case ADD -> {
                // find all assginment
                if (accounts.size() > 0) task.getAssignments().addAll(accounts);
            }
            case REMOVE -> {
                if (accounts.size() > 0) accounts.forEach(task.getAssignments()::remove);
            }
            case REST -> {
                task.getAssignments().clear();
                task.getAssignments().addAll(accounts);
            }
        }
        return ResponseHelper.success(taskRepository.save(task));
    }

    @Override
    public ResponseEntity<?> updateTaskAttachment(Long taskId, UpdateTaskAttachmentRequest request) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseHelper.notFound("Task not found");
        }
        Set<Attachment> attachments = new HashSet<>();
        for (CreateTaskAttachmentRequest attachment : request.getAttachmentRequests()) {
            Attachment newAttach = new Attachment();
            newAttach.setPath(attachment.getPath());
            attachments.add(newAttach);
        }
        switch (request.getAction()) {
            case ADD -> {
                task.getAttachments().addAll(attachmentRepository.saveAll(attachments));
            }
            case REMOVE -> {
                List<Attachment> attachments1 = attachmentRepository.findAllById(request.getRemoveIds());
                attachments1.forEach(task.getAttachments()::remove);
            }
            case REST -> {
                task.getAttachments().clear();
                task.getAttachments().addAll(attachmentRepository.saveAll(attachments));
            }
        }
        return ResponseHelper.success(taskRepository.save(task));
    }

    private Set<TaskComment> createCommentByRequest(Set<CreateTaskCommentRequest> requests) {
        Set<TaskComment> taskComments = new HashSet<>();
        for (CreateTaskCommentRequest request : requests) {
            TaskComment taskComment = new TaskComment();
            taskComment.setAttachment(request.getAttachmentRequests());
            taskComment.setAuthor(accountRepository.findById(request.getAuthorId()).orElse(null));
            taskComment.setContent(request.getContent());
            taskComments.add(taskComment);
        }
        return taskComments;
    }

    private Set<TaskStep> createTaskStepFromRequest(Set<CreateNewTaskStepRequest> requests) {
        Set<TaskStep> taskSteps = new HashSet<>();
        for (CreateNewTaskStepRequest request : requests) {
            TaskStep step = new TaskStep();
            BeanUtils.copyProperties(request, step, BeanHelper.getNullPropertyNames(request));
        }
        return taskSteps;
    }

    @Override
    public ResponseEntity<?> updateTaskComment(Long taskId, UpdateTaskCommentRequest request) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseHelper.notFound("Task not found");
        }

        Set<TaskComment> taskComments = this.createCommentByRequest(request.getCommentRequests());

        switch (request.getAction()) {
            case ADD -> {
                task.getTaskComments().addAll(taskCommentRepository.saveAll(taskComments));
            }
            case REMOVE -> {
                List<TaskComment> taskComments1 = taskCommentRepository.findAllById(request.getRemoveIds());
                taskComments1.forEach(task.getTaskComments()::remove);
            }
            case UPDATE -> {
                for (CreateTaskCommentRequest commentRequest : request.getCommentRequests()) {
                    for (TaskComment taskComment : task.getTaskComments()) {
                        if (Objects.equals(taskComment.getId(), commentRequest.getId())) {
                            taskComment.setContent(commentRequest.getContent());
                        }
                    }
                }
            }
            case REST -> {
                task.getTaskComments().clear();
                task.getTaskComments().addAll(taskCommentRepository.saveAll(taskComments));
            }
        }

        return ResponseHelper.success(taskRepository.save(task));
    }

    @Override
    public ResponseEntity<?> updateTaskStep(Long taskId, UpdateTaskStep request) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseHelper.notFound("Task not found");
        }

        Set<TaskStep> taskSteps = this.createTaskStepFromRequest(request.getTaskStepRequests());
        switch (request.getAction()) {
            case ADD -> {
                task.getSteps().addAll(taskStepRepository.saveAll(taskSteps));
            }
            case REMOVE -> {
                List<TaskStep> taskComments1 = taskStepRepository.findAllById(request.getRemoveStepsIds());
                taskComments1.forEach(task.getSteps()::remove);
            }
            case UPDATE -> {
                for (CreateNewTaskStepRequest stepRequest : request.getTaskStepRequests()) {
                    for (TaskStep step : task.getSteps()) {
                        if (Objects.equals(step.getId(), stepRequest.getId())) {
                            BeanUtils.copyProperties(stepRequest, step, BeanHelper.getNullPropertyNames(stepRequest));
                        }
                    }
                }
            }
            case REST -> {
                task.getSteps().clear();
                task.getSteps().addAll(taskStepRepository.saveAll(taskSteps));
            }
        }

        return ResponseHelper.success(taskRepository.save(task));
    }

    @Override
    public ResponseEntity<?> destroy(Long rowId, Long taskId) {
        TaskRow taskRow = taskRowRepository.findById(rowId).orElse(null);
        if (taskRow == null) {
            return ResponseHelper.notFound("Row not found");
        }
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseHelper.notFound("Task not found");
        }
        taskRow.getTasks().remove(task);
        task.getSteps().clear();
        task.getAttachments().clear();
        task.getAssignments().clear();
        task.getTaskComments().clear();
        taskRepository.delete(taskRepository.save(task));
        return ResponseHelper.success();
    }
}
