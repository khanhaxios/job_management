package com.fira.app.requests.task;

import com.fira.app.entities.TaskLabel;
import com.fira.app.enums.TaskProgress;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateNewTaskRequest {
    private String taskName;
    private String taskDescription;
    private TaskProgress taskProgress = TaskProgress.PENDING;
    private Set<String> assignmentsIds = new HashSet<>();
    private Set<CreateNewTaskStepRequest> steps = new HashSet<>();
    private Set<CreateTaskAttachmentRequest> attachments = new HashSet<>();
    private TaskLabel taskLabel;
}
