package com.fira.app.requests.task;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateNewTaskStepRequest {
    private int stepOrder;
    private String title;
    private String description;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
}
