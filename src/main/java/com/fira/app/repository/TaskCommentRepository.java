package com.fira.app.repository;

import com.fira.app.entities.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
}
