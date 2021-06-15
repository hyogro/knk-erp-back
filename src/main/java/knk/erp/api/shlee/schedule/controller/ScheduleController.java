package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.responseEntity.schedule.*;
import knk.erp.api.shlee.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    //일정 생성
    @PostMapping("")
    public ResponseEntity<RES_createSchedule> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDTO));
    }

    //일정 목록 읽기
    @GetMapping("")
    public ResponseEntity<RES_readScheduleList> readScheduleList(@RequestParam("viewOption") String viewOption) {
        return ResponseEntity.ok(scheduleService.readScheduleList(viewOption));
    }

    //일정 상세 읽기
    @GetMapping("/{sid}")
    public ResponseEntity<RES_readScheduleDetail> readScheduleDetail(@PathVariable("sid") Long sid) {
        return ResponseEntity.ok(scheduleService.readScheduleDetail(sid));
    }

    //일정 수정
    @PutMapping("/{sid}")
    public ResponseEntity<RES_updateSchedule> updateSchedule(@PathVariable("sid") Long sid, @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.updateSchedule(sid, scheduleDTO));
    }

    //일정 삭제
    @DeleteMapping("/{sid}")
    public ResponseEntity<RES_deleteSchedule> deleteSchedule(@PathVariable("sid") Long sid) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(sid));
    }
}
