package com.fira.app.services.task_row;

import com.fira.app.requests.task_row.CreateNewTaskRowRequest;
import com.fira.app.requests.task_row.UpdateNewTaskRowRequest;
import com.fira.app.services.base.ICrudService;

public interface TaskRowService extends ICrudService<CreateNewTaskRowRequest, UpdateNewTaskRowRequest, Long> {

}
