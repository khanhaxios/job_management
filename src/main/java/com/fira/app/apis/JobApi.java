package com.fira.app.apis;

import com.fira.app.requests.job.CreateJobRequest;
import com.fira.app.requests.job.UpdateJobRequest;
import com.fira.app.services.job.JobService;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/jobs")
public class JobApi {
    private final JobService jobService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','MANAGE_JOB_CREATE')")
    @PostMapping
    public ResponseEntity<?> createNewJob(@Valid @RequestBody CreateJobRequest request) {
        try {
            return jobService.store(request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','MANAGE_JOB_READ')")
    @GetMapping
    public ResponseEntity<?> getAllJob(Pageable pageable, @RequestParam(name = "sortBy", defaultValue = "jobName") String sortBy, @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDir, @RequestParam(name = "query", defaultValue = "") String query) {
        try {
            return jobService.getAll(pageable, sortBy, sortDir, query);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','MANAGE_JOB_UPDATE')")
    @PutMapping
    public ResponseEntity<?> updateJob(@Valid @RequestBody UpdateJobRequest updateJobRequest) {
        try {
            return jobService.update(updateJobRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','MANAGE_JOB_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable(name = "id") String id) {
        try {
            return jobService.destroy(id);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @GetMapping("/by-user")
    public ResponseEntity<?> getJobByUser(Pageable pageable, @RequestParam(name = "sortBy") String sortBy, @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDir, @RequestParam(name = "query", defaultValue = "") String query) {
        try {
            return jobService.getByUser(pageable, sortBy, sortDir, query);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}

