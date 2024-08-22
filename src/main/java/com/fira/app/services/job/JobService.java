package com.fira.app.services.job;

import com.fira.app.entities.Job;
import com.fira.app.requests.job.CreateJobRequest;
import com.fira.app.requests.job.UpdateJobRequest;
import com.fira.app.services.base.ICrudService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobService extends ICrudService<CreateJobRequest, UpdateJobRequest, String> {
    ResponseEntity<?> createAndPushNotification(List<Job> jobs, String content);

    ResponseEntity<?> getByUser(Pageable pageable, String sortBy, String sortDir, String query);
}
