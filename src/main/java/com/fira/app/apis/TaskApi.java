package com.fira.app.apis;

import com.fira.app.requests.task.*;
import com.fira.app.services.task.TaskService;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskApi {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateNewTaskRequest createNewTaskRequest) {
        try {
            return taskService.store(createNewTaskRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UpdateTaskRequest updateTaskRequest) {
        try {
            return taskService.update(updateTaskRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping("/update-assignment/{id}")
    public ResponseEntity<?> updateTaskAssigment(@PathVariable(name = "id") Long id, @Valid @RequestBody UpdateTaskAssignment updateTaskRequest) {
        try {
            return taskService.updateTaskAssignment(id, updateTaskRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping("/update-comment/{id}")
    public ResponseEntity<?> updateTaskComment(@PathVariable(name = "id") Long id, @Valid @RequestBody UpdateTaskCommentRequest updateTaskRequest) {
        try {
            return taskService.updateTaskComment(id, updateTaskRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping("/update-step/{id}")
    public ResponseEntity<?> updateTaskSteps(@PathVariable(name = "id") Long id, @Valid @RequestBody UpdateTaskStep updateTaskRequest) {
        try {
            return taskService.updateTaskStep(id, updateTaskRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping("/update-attachment/{id}")
    public ResponseEntity<?> updateTaskAttachment(@PathVariable(name = "id") Long id, @Valid @RequestBody UpdateTaskAttachmentRequest updateTaskRequest) {
        try {
            return taskService.updateTaskAttachment(id, updateTaskRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @DeleteMapping("/row/{rowId}/task/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "rowId") Long rowId, @PathVariable(name = "id") Long Id) {
        try {
            return taskService.destroy(rowId, Id);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

}
