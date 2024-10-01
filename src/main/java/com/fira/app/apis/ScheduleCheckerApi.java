package com.fira.app.apis;

import com.fira.app.services.schedule_checker.ScheduleCheckerService;
import com.fira.app.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/checkers")
public class ScheduleCheckerApi {

    private final ScheduleCheckerService scheduleCheckerService;

    @PostMapping("/check-in")
    public ResponseEntity<?> checkIn() {
        try {
            return scheduleCheckerService.checkIn();
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> checkOut() {
        try {
            return scheduleCheckerService.checkout();
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<?> getByUser(Pageable pageable) {
        try {
            return scheduleCheckerService.getScheduleTimeByUser(pageable);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @GetMapping("/get-by-user-today")
    public ResponseEntity<?> getToday() {
        try {
            return scheduleCheckerService.getScheduleToDay();
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
