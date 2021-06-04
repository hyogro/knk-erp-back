package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Attendance.AttendanceDTO;
import knk.erp.api.shlee.schedule.dto.Attendance.RES_offWork;
import knk.erp.api.shlee.schedule.dto.Attendance.RES_onWork;
import knk.erp.api.shlee.schedule.dto.Attendance.RES_readAttendanceList;
import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.service.AttendanceService;
import knk.erp.api.shlee.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    AttendanceService attendanceService;

    AttendanceController(AttendanceService attendanceService){
        this.attendanceService = attendanceService;
    }

    /** 출근 기록 **/
    @PostMapping("/onWork")
    public ResponseEntity<RES_onWork> onWork(@RequestBody AttendanceDTO attendanceDTO){
        return ResponseEntity.ok(attendanceService.onWork(attendanceDTO));
    }
    /** 퇴근 기록 **/
    @PostMapping("/offWork")
    public ResponseEntity<RES_offWork> offWork(@RequestBody AttendanceDTO attendanceDTO){
        return ResponseEntity.ok(attendanceService.offWork(attendanceDTO));
    }

    /** 출,퇴근 기록목록 조회 **/
    @PostMapping("/readAttendanceList")
    public ResponseEntity<RES_readAttendanceList> readAttendanceList(@RequestBody AttendanceDTO attendanceDTO){
        return ResponseEntity.ok(attendanceService.readAttendanceList(attendanceDTO));
    }

    /** 출,퇴근 기록 정정 요청**/
}
