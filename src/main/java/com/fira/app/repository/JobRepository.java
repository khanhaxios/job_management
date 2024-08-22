package com.fira.app.repository;

import com.fira.app.entities.Account;
import com.fira.app.entities.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, String> {
    Page<Job> findAllByJobNameContaining(Pageable pageable, String name);

    Page<Job> findAllByMembers(Pageable pageable, Account member);

    Page<Job> findAllByJobNameContainingAndMembers(Pageable pageable, String name, Account account);
}
