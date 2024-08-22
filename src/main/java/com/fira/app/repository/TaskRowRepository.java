package com.fira.app.repository;

import com.fira.app.entities.TaskRow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRowRepository extends JpaRepository<TaskRow, Long> {
    TaskRow findByIdAndDisplay(Long Id, boolean display);

    Page<TaskRow> findAllByRowName(Pageable pageable, String rowName);

}
