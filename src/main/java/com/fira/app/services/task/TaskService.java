package com.fira.app.services.task;

import com.fira.app.entities.Task;
import com.fira.app.requests.task.CreateNewTaskRequest;
import com.fira.app.requests.task.UpdateTaskRequest;
import com.fira.app.services.base.ICrudService;


public interface TaskService extends ICrudService<CreateNewTaskRequest, UpdateTaskRequest, Long> {
}
