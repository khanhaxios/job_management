package com.fira.app.repository;

import com.fira.app.entities.Account;
import com.fira.app.entities.Job;
import com.fira.app.entities.Task;
import com.fira.app.entities.TaskRow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, String> {
    Page<Job> findAllByJobNameContaining(Pageable pageable, String name);

    Job findByTaskRowsContaining(TaskRow taskRow);

    Page<Job> findAllByMembers(Pageable pageable, Account member);

    Page<Job> findAllByJobNameContainingAndMembers(Pageable pageable, String name, Account account);
}
