package com.fira.app.services.task;

import com.fira.app.requests.task.CreateNewTaskRequest;
import com.fira.app.requests.task.UpdateTaskRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Override
    public ResponseEntity<?> store(CreateNewTaskRequest createNewTaskRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> update(UpdateTaskRequest updateTaskRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> destroyAll(Set<Long> longs) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable, String fieldSort, String sortDesc, String query) throws Exception {
        return null;
    }
}
