package com.fira.app.repository;

import com.fira.app.entities.TaskLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
}
