package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.service.ScheduleServiceImpl;
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
        return "gateway schedule hello!";
    }

    @PostMapping("/index")
    public String index(){
        return "this is schedule index!";
    }



}
