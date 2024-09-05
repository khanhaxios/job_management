package com.fira.app.repository;

import com.fira.app.entities.TaskStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStepRepository extends JpaRepository<TaskStep, Long> {
}
