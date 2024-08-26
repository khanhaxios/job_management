package com.fira.app.services.task_row;

import com.fira.app.entities.Job;
import com.fira.app.entities.TaskRow;
import com.fira.app.repository.JobRepository;
import com.fira.app.repository.TaskRepository;
import com.fira.app.repository.TaskRowRepository;
import com.fira.app.requests.task_row.CreateNewTaskRowRequest;
import com.fira.app.requests.task_row.UpdateNewTaskRowRequest;
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
    public ResponseEntity<?> getAll(Pageable pageable, String fieldSort, String sortDesc, String query) throws Exception {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDesc), fieldSort);
        Pageable pageOf = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        if (!Objects.equals(query, "")) {
            return ResponseHelper.success(taskRowRepository.findAllByRowName(pageOf, query));
        }
        return ResponseHelper.success(taskRowRepository.findAll(pageable));
    }
}
