package com.fira.app.entities;

import com.fira.app.enums.TaskProgress;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Task extends TimeStamps {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    private String taskName;

    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private TaskProgress taskProgress;

    @ManyToMany
    private Set<Account> assignments = new HashSet<>();

    @OneToMany
    private Set<TaskStep> steps = new HashSet<>();

    @OneToMany
    private Set<Attachment> attachments = new HashSet<>();

    @OneToMany
    private Set<TaskComment> taskComments = new HashSet<>();

    @ManyToOne
    private TaskLabel taskLabel;
}
