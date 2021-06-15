package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Attendance.*;
import knk.erp.api.shlee.schedule.responseEntity.attendance.*;
import knk.erp.api.shlee.schedule.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    AttendanceService attendanceService;

    AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * 출근 기록
     **/
    @PostMapping("/onWork")
    public ResponseEntity<RES_onWork> onWork() {
        return ResponseEntity.ok(attendanceService.onWork());
    }

    /**
     * 퇴근 기록
     **/
    @PostMapping("/offWork")
    public ResponseEntity<RES_offWork> offWork() {
        return ResponseEntity.ok(attendanceService.offWork());
    }

    /**
     * 출,퇴근 기록목록 조회
     **/
    @GetMapping("")
    public ResponseEntity<RES_readAttendanceList> readAttendanceList() {
        return ResponseEntity.ok(attendanceService.readAttendanceList());
    }

    /**
     * 출,퇴근 기록 조회
     **/
    @PostMapping("/{aid}")
    public ResponseEntity<RES_readAttendance> readAttendance(@PathVariable("aid") Long aid) {
        return ResponseEntity.ok(attendanceService.readAttendance(aid));
    }

    /**
     * 출,퇴근 기록 정정 요청
     **/
    @PostMapping("/rectifyAttendance")
    public ResponseEntity<RES_createRectifyAttendance> createRectifyAttendance(@RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.createRectifyAttendance(rectifyAttendanceDTO));
    }

    /**
     * 퇴근 기록 정정 요청
     **/
    @PostMapping("/rectifyAttendance/{aid}")
    public ResponseEntity<RES_updateRectifyAttendance> updateRectifyAttendance(@PathVariable("aid") Long aid, @RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.updateRectifyAttendance(aid, rectifyAttendanceDTO));
    }

    /**
     * 출,퇴근 기록 정정요청목록 조회
     **/
    @GetMapping("/rectify")
    public ResponseEntity<RES_readRectifyAttendanceList> readRectifyAttendanceList() {
        return ResponseEntity.ok(attendanceService.readRectifyAttendanceList());
    }

    /**
     * 출,퇴근 기록 정정요청상세 조회
     **/
    @GetMapping("/rectify/{rid}")
    public ResponseEntity<RES_readRectifyAttendance> readRectifyAttendance(@PathVariable("rid") Long rid) {
        return ResponseEntity.ok(attendanceService.readRectifyAttendance(rid));
    }

    /**
     * 출,퇴근 기록 정정요청 삭제
     **/
    @DeleteMapping("/rectify/{rid}")
    public ResponseEntity<RES_deleteRectifyAttendance> deleteRectifyAttendance(@PathVariable Long rid) {
        return ResponseEntity.ok(attendanceService.deleteRectifyAttendance(rid));
    }

    /**
     * 승인해야할 출,퇴근 정정요청목록 조회
     **/
    @GetMapping("/rectify/approve")
    public ResponseEntity<RES_readRectifyAttendanceList> readRectifyAttendanceListForApprove() {
        return ResponseEntity.ok(attendanceService.readRectifyAttendanceListForApprove());
    }

    /**
     * 출,퇴근 기록 정정요청 승인
     **/
    @PutMapping("/rectify/approve/{rid}")
    public ResponseEntity<RES_approveRectifyAttendance> approveRectifyAttendance(@PathVariable Long rid) {
        return ResponseEntity.ok(attendanceService.approveRectifyAttendance(rid));
    }

    /**
     * 출,퇴근 기록 요약정보 조회
     **/
    @GetMapping("summary")
    public ResponseEntity<RES_readAttendanceSummary> readAttendanceSummary() {
        return ResponseEntity.ok(attendanceService.readAttendanceSummary());
    }

    /**
     * 개인 출,퇴근 당일정보 조회
     **/
    @GetMapping("today")
    public ResponseEntity<RES_readAttendance> readAttendanceToday() {
        return ResponseEntity.ok(attendanceService.readAttendanceToday());
    }
}
