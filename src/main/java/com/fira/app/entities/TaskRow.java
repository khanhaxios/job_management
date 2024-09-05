package com.fira.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class TaskRow extends TimeStamps {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String rowName;

    private boolean display = true;

    @OneToMany
    private Set<Task> tasks = new HashSet<>();
    private int inOrder;

    public void addTask(Task savedTask) {
        this.tasks.add(savedTask);
    }
}
