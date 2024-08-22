package com.fira.app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "jobs")
public class Job extends TimeStamps {
    @jakarta.persistence.Id
    private String Id;

    public Job() {
        this.Id = UUID.randomUUID().toString();
    }

    @NotNull
    private String jobName;

    @NotNull
    private LocalDate timeStart;

    @NotNull
    private LocalDate timeEnd;

    @ManyToOne
    private Account manager;
    @ManyToMany
    private Set<Account> members = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ContributesHistory> contributesHistories = new HashSet<>();
    @OneToMany
    private Set<TaskRow> taskRows = new HashSet<>();

    @OneToMany
    private Set<TaskLabel> taskLabels = new HashSet<>();

    public void addMember(Account account) {
        this.members.add(account);
    }

    public void removeMember(Account account) {
        this.members.remove(account);
    }

    public void addRow(TaskRow taskRow) {
        this.taskRows.add(taskRow);
    }

    public void removeRow(TaskRow taskRow) {
        this.taskRows.remove(taskRow);
    }
}
