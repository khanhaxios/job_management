package com.fira.app.requests.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fira.app.enums.StepStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateNewTaskStepRequest {
    private Long Id;
    private int stepOrder;
    private String title;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timeStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timeEnd;

    private StepStatus status = StepStatus.PENDING;
}
