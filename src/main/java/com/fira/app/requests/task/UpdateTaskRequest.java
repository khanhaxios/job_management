package com.fira.app.requests.task;

import com.fira.app.entities.TaskLabel;
import com.fira.app.enums.TaskProgress;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskRequest {

    private String taskName;

    private String taskDescription;

    private LocalDate timeStart;
    private LocalDate timeEnd;
    private TaskProgress taskProgress;

    private Long taskLabelId;
    private Long Id;
}
