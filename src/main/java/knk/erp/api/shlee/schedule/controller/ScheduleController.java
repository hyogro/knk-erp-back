package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.service.ScheduleService;
import org.springframework.http.HttpRequest;
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

    //
    @PostMapping("/createSchedule")
    public ResponseEntity<RES_createSchedule> createSchedule(@RequestBody ScheduleDTO scheduleDTO){
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDTO));
    }

    @PostMapping("/readScheduleList")
    public ResponseEntity<RES_readScheduleList> readScheduleList(){
        return ResponseEntity.ok(scheduleService.readScheduleList());
    }

    @PostMapping("/readScheduleDetail")
    public ResponseEntity<RES_readScheduleDetail> readScheduleDetail(@RequestBody ScheduleDTO scheduleDTO){
        return ResponseEntity.ok(scheduleService.readScheduleDetail(scheduleDTO));
    }

    @PostMapping("/updateSchedule")
    public ResponseEntity<RES_updateSchedule> updateSchedule(@RequestBody ScheduleDTO scheduleDTO, @RequestHeader(value = "token") String token){
        System.out.print(token);
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleDTO));
    }
    @PostMapping("/deleteSchedule")
    public ResponseEntity<RES_deleteSchedule> deleteSchedule(@RequestBody ScheduleDTO scheduleDTO){
        return ResponseEntity.ok(scheduleService.deleteSchedule(scheduleDTO));
    }
}
