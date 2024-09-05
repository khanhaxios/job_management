package com.fira.app.services.task_row;

import com.fira.app.requests.task_row.CreateNewTaskRowRequest;
import com.fira.app.requests.task_row.UpdateNewTaskRowRequest;
import com.fira.app.requests.task_row.UpdateTaskInRowRequest;
import com.fira.app.services.base.ICrudService;
import org.springframework.http.ResponseEntity;

public interface TaskRowService extends ICrudService<CreateNewTaskRowRequest, UpdateNewTaskRowRequest, Long> {
    ResponseEntity<?> updateTaskInRow(Long rowId, UpdateTaskInRowRequest request);
}
