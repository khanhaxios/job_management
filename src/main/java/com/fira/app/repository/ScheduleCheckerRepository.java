package com.fira.app.repository;

import com.fira.app.entities.Account;
import com.fira.app.entities.ScheduleChecker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleCheckerRepository extends JpaRepository<ScheduleChecker, Long> {
    List<ScheduleChecker> findAllByUserCheckedAndCheckedAtBetween(Account userChecked, LocalDateTime timeStart, LocalDateTime timeEnd);

    Page<ScheduleChecker> findAllByUserChecked(Pageable pageable, Account userChecked);
}
