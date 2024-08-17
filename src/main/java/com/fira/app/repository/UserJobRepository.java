package com.fira.app.repository;

import com.fira.app.entities.Account;
import com.fira.app.entities.UserJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJobRepository extends JpaRepository<UserJob, String> {
    UserJob findByUserAndJobId(Account user, String jobId);
}
