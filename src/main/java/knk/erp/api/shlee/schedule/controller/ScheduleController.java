package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //일정 생성
    @PostMapping("")
    public ResponseEntity<ResponseCM> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDTO));
    }

    //일정 목록 읽기
    @GetMapping("")
    public ResponseEntity<ResponseCMDL> readScheduleList(@RequestParam("viewOption") String viewOption,
                                                         @RequestParam("startDate") LocalDateTime startDate,
                                                         @RequestParam("endDate") LocalDateTime endDate) {
        return ResponseEntity.ok(scheduleService.readScheduleList(viewOption, startDate, endDate));
    }

    //일정 상세 읽기
    @GetMapping("/{sid}")
    public ResponseEntity<ResponseCMD> readScheduleDetail(@PathVariable("sid") Long sid) {
        return ResponseEntity.ok(scheduleService.readScheduleDetail(sid));
    }

    //일정 수정
    @PutMapping("/{sid}")
    public ResponseEntity<ResponseCM> updateSchedule(@PathVariable("sid") Long sid, @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.updateSchedule(sid, scheduleDTO));
    }

    //일정 삭제
    @DeleteMapping("/{sid}")
    public ResponseEntity<ResponseCM> deleteSchedule(@PathVariable("sid") Long sid) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(sid));
    }
}
