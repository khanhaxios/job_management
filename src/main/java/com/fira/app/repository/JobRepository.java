package com.fira.app.repository;

import com.fira.app.entities.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobRepository extends JpaRepository<Job, String> {
    Page<Job> findByTitleContaining(Pageable pageable, String title);
}
