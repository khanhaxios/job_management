package com.fira.app.services.task;

import com.fira.app.entities.Task;
import com.fira.app.requests.task.*;
import com.fira.app.services.base.ICrudService;
import org.springframework.http.ResponseEntity;


public interface TaskService extends ICrudService<CreateNewTaskRequest, UpdateTaskRequest, Long> {
    ResponseEntity<?> updateTaskAssignment(Long taskId, UpdateTaskAssignment updateTaskAssignment);

    ResponseEntity<?> updateTaskAttachment(Long taskId, UpdateTaskAttachmentRequest request);

    ResponseEntity<?> updateTaskComment(Long taskId, UpdateTaskCommentRequest request);

    ResponseEntity<?> updateTaskStep(Long taskId, UpdateTaskStep request);

    ResponseEntity<?> destroy(Long rowId, Long taskId);
}
