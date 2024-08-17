package com.fira.app.services.job;

import com.fira.app.requests.job.*;
import com.fira.app.services.base.ICrudService;
import org.springframework.http.ResponseEntity;

public interface JobService extends ICrudService<CreateJobRequest, UpdateJobRequest, String> {
    ResponseEntity<?> giveJobForUser(String jobId, GiveJobForUserRequest request) throws Exception;

    ResponseEntity<?> evoluteJob(String jobId, EvoluteJobRequest request) throws Exception;

    ResponseEntity<?> updateUserJobDetailRequest(String jobId, UpdateUserJobDetailRequest request) throws Exception;

    ResponseEntity<?> getById(String s) throws Exception;
}
