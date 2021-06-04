package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/hello")
    public String hello(){
        return "gateway schedule hello!";
    }

    /** 일정 생성 **/
    @PostMapping("/createSchedule")
    public ResponseEntity<RES_createSchedule> createSchedule(@RequestBody ScheduleDTO scheduleDTO){
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDTO));
    }

    /** 일정 목록 읽기 **/
    @PostMapping("/readScheduleList")
    public ResponseEntity<RES_readScheduleList> readScheduleList(){
        return ResponseEntity.ok(scheduleService.readScheduleList());
    }

    /** 일정 상세 읽기 **/
    @PostMapping("/readScheduleDetail")
    public ResponseEntity<RES_readScheduleDetail> readScheduleDetail(@RequestBody ScheduleDTO scheduleDTO){
        return ResponseEntity.ok(scheduleService.readScheduleDetail(scheduleDTO));
    }

    /**
     * 일정 수정
     * 권한 체크
     **/
    @PostMapping("/updateSchedule")
    public ResponseEntity<RES_updateSchedule> updateSchedule(@RequestBody ScheduleDTO scheduleDTO, @RequestHeader(value = "token") String token){
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleDTO, token.substring(7)));
    }

    /**
     * 일정 삭제
     * 권한 체크
     **/
    @PostMapping("/deleteSchedule")
    public ResponseEntity<RES_deleteSchedule> deleteSchedule(@RequestBody ScheduleDTO scheduleDTO, @RequestHeader(value = "token") String token){
        return ResponseEntity.ok(scheduleService.deleteSchedule(scheduleDTO, token.substring(7)));
    }
}
