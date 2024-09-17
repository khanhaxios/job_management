package com.fira.app.services.task_row;

import com.fira.app.entities.Job;
import com.fira.app.entities.TaskRow;
import com.fira.app.repository.JobRepository;
import com.fira.app.repository.TaskRepository;
import com.fira.app.repository.TaskRowRepository;
import com.fira.app.requests.task_row.CreateNewTaskRowRequest;
import com.fira.app.requests.task_row.UpdateNewTaskRowRequest;
import com.fira.app.requests.task_row.UpdateTaskInRowRequest;
import com.fira.app.utils.BeanHelper;
import com.fira.app.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskRowServiceImpl implements TaskRowService {
    private final TaskRowRepository taskRowRepository;
    private final JobRepository jobRepository;

    private final TaskRepository taskRepository;

    @Override
    public ResponseEntity<?> store(CreateNewTaskRowRequest createNewTaskRowRequest) throws Exception {
        TaskRow taskRow = new TaskRow();
        taskRow.setRowName(createNewTaskRowRequest.getRowName());
        taskRow.setInOrder(createNewTaskRowRequest.getOrder());
        TaskRow savedRow = taskRowRepository.save(taskRow);
        Job job = jobRepository.findById(createNewTaskRowRequest.getJobId()).orElse(null);
        if (job == null) {
            return ResponseHelper.notFound("Job not found");
        }
        job.addRow(savedRow);
        jobRepository.save(job);
        return ResponseHelper.success(savedRow);
    }

    @Override
    public ResponseEntity<?> update(UpdateNewTaskRowRequest updateNewTaskRowRequest) throws Exception {
        TaskRow taskRow = taskRowRepository.findById(updateNewTaskRowRequest.getId()).orElse(null);
        if (taskRow == null) {
            return ResponseHelper.notFound("Task row not found");
        }
        BeanUtils.copyProperties(updateNewTaskRowRequest, taskRow, BeanHelper.getNullPropertyNames(updateNewTaskRowRequest));
        taskRow.setInOrder(updateNewTaskRowRequest.getOrder());
        return ResponseHelper.success(taskRowRepository.save(taskRow));
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) throws Exception {
        TaskRow taskRow = taskRowRepository.findById(aLong).orElse(null);
        if (taskRow == null) {
            return ResponseHelper.notFound("Task row not found");
        }
        Job job = jobRepository.findByTaskRowsContaining(taskRow);
        if (job == null) {
            return ResponseHelper.notFound("Job not found");
        }
        job.removeRow(taskRow);
        jobRepository.save(job);
        taskRow.getTasks().clear();
        taskRowRepository.save(taskRow);
        taskRowRepository.deleteById(aLong);
        return ResponseHelper.success();
    }

    @Override
    public ResponseEntity<?> destroyAll(Set<Long> longs) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> getRowByJob(String jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            return ResponseHelper.notFound("Job not found by id " + jobId);
        }
        return ResponseHelper.success(job.getTaskRows());
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable, String fieldSort, String sortDesc, String query) throws Exception {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDesc), fieldSort);
        Pageable pageOf = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        if (!Objects.equals(query, "")) {
            return ResponseHelper.success(taskRowRepository.findAllByRowName(pageOf, query));
        }
        return ResponseHelper.success(taskRowRepository.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> updateTaskInRow(Long rowId, UpdateTaskInRowRequest request) {
        TaskRow taskRow = taskRowRepository.findById(rowId).orElse(null);
        if (taskRow == null) {
            return ResponseHelper.notFound("Row not found");
        }
        switch (request.getAction()) {
            case REMOVE -> {
                taskRepository.findAllById(request.getTaskIds()).forEach(taskRow.getTasks()::remove);
            }
            case REST -> {
                taskRow.getTasks().clear();
                taskRow.getTasks().addAll(taskRepository.findAllById(request.getTaskIds()));
            }
        }
        return ResponseHelper.success(taskRowRepository.save(taskRow));
    }
}
