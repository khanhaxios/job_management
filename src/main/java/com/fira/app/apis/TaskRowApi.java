package com.fira.app.apis;

import com.fira.app.requests.task_row.CreateNewTaskRowRequest;
import com.fira.app.requests.task_row.UpdateNewTaskRowRequest;
import com.fira.app.requests.task_row.UpdateTaskInRowRequest;
import com.fira.app.services.task_row.TaskRowService;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task_rows")
@RequiredArgsConstructor
@CrossOrigin
public class TaskRowApi {
    private final TaskRowService taskRowService;

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable, @RequestParam(name = "sortBy", defaultValue = "jobName") String sortBy, @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDir, @RequestParam(name = "query", defaultValue = "") String query) {
        try {
            return taskRowService.getAll(pageable, sortBy, sortDir, query);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateNewTaskRowRequest request) {
        try {
            return taskRowService.store(request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @GetMapping("/by-job/{jobId}")
    public ResponseEntity<?> getByJob(@PathVariable(name = "jobId") String jobId) {
        try {
            return taskRowService.getRowByJob(jobId);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@Valid @RequestBody UpdateNewTaskRowRequest request) {
        try {
            return taskRowService.update(request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTasks(@PathVariable(name = "id") Long id, @Valid @RequestBody UpdateTaskInRowRequest request) {
        try {
            return taskRowService.updateTaskInRow(id, request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        try {
            return taskRowService.destroy(id);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
