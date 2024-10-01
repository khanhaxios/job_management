package com.fira.app.services.schedule_checker;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface ScheduleCheckerService {
    public ResponseEntity<?> checkIn();

    public ResponseEntity<?> checkout();

    public ResponseEntity<?> getScheduleTimeByUser(Pageable pageable);


    public ResponseEntity<?> getScheduleToDay();
}
