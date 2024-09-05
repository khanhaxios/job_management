package com.fira.app.entities;

import com.fira.app.enums.StepStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class TaskStep {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private int stepOrder;
    private String title;

    private String description;

    private StepStatus status;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
}
