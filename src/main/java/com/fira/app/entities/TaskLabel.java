package com.fira.app.entities;

import com.fira.app.enums.LabelType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "task_labels")
public class TaskLabel {
    @jakarta.persistence.Id
    private Long Id;

    @NotNull
    private String labelName;

    @Enumerated(EnumType.STRING)
    private LabelType type;

    @NotNull
    private String labelColor;
}
