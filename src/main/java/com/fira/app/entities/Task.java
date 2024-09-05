package com.fira.app.entities;

import com.fira.app.enums.TaskProgress;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
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

    private LocalDate timeStart;
    private LocalDate timeEnd;
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

    public void addAssgiment(Account account) {
        this.assignments.add(account);
    }

    public void removeAssgiment(Account account) {
        this.assignments.remove(account);
    }

    public void addComment(TaskComment taskComment) {
        this.taskComments.add(taskComment);
    }

    public void removeComment(TaskComment taskComment) {
        this.taskComments.remove(taskComment);
    }
}
