package com.fira.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fira.app.enums.CheckInSchedule;
import com.fira.app.enums.ScheduleCheckType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "schedule_checkers")
public class ScheduleChecker {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private LocalDateTime checkedAt;

    @Enumerated(EnumType.STRING)
    private ScheduleCheckType checkType;

    @Enumerated(EnumType.STRING)
    private CheckInSchedule checkInSchedule;

    private long timeLate;
    private long timeEarly;

    @ManyToOne
    @JsonIgnore
    private Account userChecked;
}
