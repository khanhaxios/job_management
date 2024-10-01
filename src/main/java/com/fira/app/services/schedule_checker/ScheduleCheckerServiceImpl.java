package com.fira.app.services.schedule_checker;

import com.fira.app.entities.Account;
import com.fira.app.entities.ScheduleChecker;
import com.fira.app.enums.CheckInSchedule;
import com.fira.app.enums.ScheduleCheckType;
import com.fira.app.repository.AccountRepository;
import com.fira.app.repository.ScheduleCheckerRepository;
import com.fira.app.utils.ResponseHelper;
import com.fira.app.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ScheduleCheckerServiceImpl implements ScheduleCheckerService {
    private final ScheduleCheckerRepository checkerRepository;

    private final AccountRepository accountRepository;

    private CheckInSchedule getByTime(LocalDateTime time) {
        if (time.getHour() >= 6 && time.getHour() <= 10) {
            return CheckInSchedule.MORNING;
        }
        if (time.getHour() >= 10 && time.getHour() <= 18) {
            return CheckInSchedule.AFTERNOON;
        }
        if (time.getHour() >= 18) {
            return CheckInSchedule.NIGHT;
        }
        return CheckInSchedule.MORNING;
    }

    private int[] getHoursRangerByCheckSchedule(CheckInSchedule checkInSchedule) {
        switch (checkInSchedule) {
            case MORNING -> {
                return new int[]{6, 10};
            }
            case NIGHT -> {
                return new int[]{10, 18};
            }
            case AFTERNOON -> {
                return new int[]{18, 23};
            }
        }
        return new int[]{6, 10};
    }

    @Override
    public ResponseEntity<?> checkIn() {
        // just checked in
        // first get current user
        LocalDateTime now = LocalDateTime.now();
        Account account = SecurityHelper.getAccountFromLogged(accountRepository);
        if (account == null) {
            return ResponseHelper.accessDenied("Required auth token for this api");
        }
        // check now time
        CheckInSchedule checkInSchedule = this.getByTime(now);
        int[] hoursByCheckIn = this.getHoursRangerByCheckSchedule(checkInSchedule);
        LocalDateTime timeStart = LocalDateTime.now().withHour(hoursByCheckIn[0]);
        LocalDateTime timeEnd = LocalDateTime.now().withHour(hoursByCheckIn[1]);
        List<ScheduleChecker> isExists = checkerRepository.findAllByUserCheckedAndCheckedAtBetween(account, timeStart, timeEnd);
        if (isExists != null) {
            if (isExists.stream().filter(s -> s.getCheckType().equals(ScheduleCheckType.CHECKIN)).collect(Collectors.toList()).size() > 0) {
                return ResponseHelper.badRequest("U'r checked in");
            }
        }
        ScheduleChecker scheduleChecker = new ScheduleChecker();
        scheduleChecker.setCheckedAt(now);
        scheduleChecker.setCheckInSchedule(checkInSchedule);
        scheduleChecker.setUserChecked(account);
        scheduleChecker.setCheckType(ScheduleCheckType.CHECKIN);
        return ResponseHelper.success(checkerRepository.save(scheduleChecker));
    }

    @Override
    public ResponseEntity<?> checkout() {
        // just checked in
        // first get current user
        LocalDateTime now = LocalDateTime.now();
        Account account = SecurityHelper.getAccountFromLogged(accountRepository);
        if (account == null) {
            return ResponseHelper.accessDenied("Required auth token for this api");
        }
        // check now time
        CheckInSchedule checkInSchedule = this.getByTime(now);
        int[] hoursByCheckIn = this.getHoursRangerByCheckSchedule(checkInSchedule);
        LocalDateTime timeStart = LocalDateTime.now().withHour(hoursByCheckIn[0]);
        LocalDateTime timeEnd = LocalDateTime.now().withHour(hoursByCheckIn[1]);

        List<ScheduleChecker> isExists = checkerRepository.findAllByUserCheckedAndCheckedAtBetween(account, timeStart, timeEnd);
        if (isExists != null) {
            if (isExists.stream().filter(s -> s.getCheckType().equals(ScheduleCheckType.CHECKIN)).toList().size() == 0) {
                return ResponseHelper.badRequest("U didn't check in please checkin first to checkout");
            }
            if (isExists.size() >= 2) {
                return ResponseHelper.badRequest("U're checked out");
            }
        }

        ScheduleChecker scheduleChecker = new ScheduleChecker();
        scheduleChecker.setCheckedAt(now);
        scheduleChecker.setCheckInSchedule(checkInSchedule);
        scheduleChecker.setUserChecked(account);
        scheduleChecker.setCheckType(ScheduleCheckType.CHECKOUT);
        return ResponseHelper.success(checkerRepository.save(scheduleChecker));
    }


    @Override
    public ResponseEntity<?> getScheduleTimeByUser(Pageable pageable) {
        Account account = SecurityHelper.getAccountFromLogged(accountRepository);
        if (account == null) {
            return ResponseHelper.accessDenied("Required auth token for this api");
        }
        return ResponseHelper.success(checkerRepository.findAllByUserChecked(pageable, account));
    }

    @Override
    public ResponseEntity<?> getScheduleToDay() {
        Account account = SecurityHelper.getAccountFromLogged(accountRepository);
        if (account == null) {
            return ResponseHelper.accessDenied("Required auth token for this api");
        }
        LocalDateTime timeStart = LocalDateTime.now().withHour(0);
        LocalDateTime timeEnd = LocalDateTime.now().withHour(23);
        return ResponseHelper.success(checkerRepository.findAllByUserCheckedAndCheckedAtBetween(account, timeStart, timeEnd));

    }
}
