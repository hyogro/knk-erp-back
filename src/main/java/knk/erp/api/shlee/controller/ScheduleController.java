package knk.erp.api.shlee.controller;

import knk.erp.api.shlee.service.ScheduleServiceImpl;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    ScheduleServiceImpl scheduleService;

    ScheduleController(ScheduleServiceImpl scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/hello")
    public String hello(){
        return "gateway schedule hello!!";
    }

    @PostMapping("/index")
    public String index(){
        return "this is schedule index!";
    }

    @PostMapping("/isWork")
    public String callSchedule(){
        return scheduleService.callSchedule();
    }

    @PostMapping("/createSchedule")
    public String createSchedule(@RequestBody HashMap<String, String> requestData){
        JSONObject jb = new JSONObject(requestData);
        return scheduleService.createSchedule(jb);
    }


}
