package com.fira.app.repository;

import com.fira.app.entities.JobDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDetailRepository extends JpaRepository<JobDetail, String> {
}
