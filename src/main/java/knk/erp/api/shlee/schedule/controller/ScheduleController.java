package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Schedule.*;
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

//    @PostMapping("/createSchedule")
//    public ResponseEntity<RES_createSchedule> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
//        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDTO));
//    }

//    @PostMapping("/readScheduleList")
//    public ResponseEntity<RES_readScheduleList> readScheduleList(@RequestBody REQ_readScheduleListOption readScheduleListOption) {
//        return ResponseEntity.ok(scheduleService.readScheduleList(readScheduleListOption));
//    }

//    @PostMapping("/readScheduleDetail")
//    public ResponseEntity<RES_readScheduleDetail> readScheduleDetail(@RequestBody ScheduleDTO scheduleDTO) {
//        return ResponseEntity.ok(scheduleService.readScheduleDetail(scheduleDTO));
//    }

//    @PostMapping("/updateSchedule")
//    public ResponseEntity<RES_updateSchedule> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
//        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleDTO));
//    }

//    @PostMapping("/deleteSchedule")
//    public ResponseEntity<RES_deleteSchedule> deleteSchedule(@RequestBody ScheduleDTO scheduleDTO) {
//        return ResponseEntity.ok(scheduleService.deleteSchedule(scheduleDTO));
//    }

    /**
     * 일정 생성
     **/
    @PostMapping("")
    public ResponseEntity<RES_createSchedule> createSchedule_t(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDTO));
    }

    /**
     * 일정 목록 읽기
     **/
    @GetMapping("")
    public ResponseEntity<RES_readScheduleList> readScheduleList_t(@RequestBody REQ_readScheduleListOption readScheduleListOption) {
        return ResponseEntity.ok(scheduleService.readScheduleList(readScheduleListOption));
    }

    /**
     * 일정 상세 읽기
     **/
    @GetMapping("/{sid}")
    public ResponseEntity<RES_readScheduleDetail> readScheduleDetail_t(@PathVariable("sid") Long sid) {
        return ResponseEntity.ok(scheduleService.readScheduleDetail(sid));
    }

    /**
     * 일정 수정
     **/
    @PutMapping("/{sid}")
    public ResponseEntity<RES_updateSchedule> updateSchedule_t(@PathVariable("sid") Long sid, @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.updateSchedule(sid, scheduleDTO));
    }

    /**
     * 일정 삭제
     **/
    @DeleteMapping("/{sid}")
    public ResponseEntity<RES_deleteSchedule> deleteSchedule_t(@PathVariable("sid") Long sid) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(sid));
    }
}
