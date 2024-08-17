package com.fira.app.apis;

import com.fira.app.requests.job.*;
import com.fira.app.services.job.JobService;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
@CrossOrigin
@RequiredArgsConstructor
public class JobApi {
    private final JobService jobService;

    @PreAuthorize("hasAuthority('MANAGE_JOB_CREATE')")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CreateJobRequest requestBody) {
        try {
            return jobService.store(requestBody);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_JOB_UPDATE')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody UpdateJobRequest requestBody, @PathVariable(name = "id") String Id) {
        try {
            return jobService.update(requestBody);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_JOB_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String Id) {
        try {
            return jobService.destroy(Id);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_JOB_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") String s) {
        try {
            return jobService.getById(s);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_JOB_READ')")
    @GetMapping
    public ResponseEntity<?> getAllJob(@RequestParam(name = "query", required = false, defaultValue = "") String query, @RequestParam(name = "sortBy", defaultValue = "name") String sortBy, @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection, Pageable pageable) {
        try {
            return jobService.getAll(pageable, sortBy, sortDirection, query);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_JOB_UPDATE')")
    @PutMapping("/user-job/{id}")
    public ResponseEntity<?> updateJobDetailForUser(@PathVariable(name = "id") String id, @RequestBody UpdateUserJobDetailRequest request) {
        try {
            return jobService.updateUserJobDetailRequest(id, request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_JOB_UPDATE')")
    @PutMapping("/give-for-user/{id}")
    public ResponseEntity<?> giveJobForUser(@PathVariable(name = "id") String id, @RequestBody GiveJobForUserRequest request) {
        try {
            return jobService.giveJobForUser(id, request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());

        }
    }

    @PreAuthorize("hasAuthority('MANAGE_JOB_UPDATE')")
    @PutMapping("/evaluate-job/{id}")
    public ResponseEntity<?> evaluateJobForUser(@PathVariable(name = "id") String jobId, @RequestBody EvoluteJobRequest request) {
        try {
            return jobService.evoluteJob(jobId, request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());

        }
    }


}
