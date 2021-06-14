package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Attendance.*;
import knk.erp.api.shlee.schedule.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/readAttendanceList")
    public ResponseEntity<RES_readAttendanceList> readAttendanceList() {
        return ResponseEntity.ok(attendanceService.readAttendanceList());
    }

    /**
     * 출,퇴근 기록 조회
     **/
    @PostMapping("/readAttendance")
    public ResponseEntity<RES_readAttendance> readAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        return ResponseEntity.ok(attendanceService.readAttendance(attendanceDTO));
    }

    /**
     * 출,퇴근 기록 정정 요청
     **/
    @PostMapping("createRectifyAttendance")
    public ResponseEntity<RES_createRectifyAttendance> createRectifyAttendance(@RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.createRectifyAttendance(rectifyAttendanceDTO));
    }

    /**
     * 퇴근 기록 정정 요청
     **/
    @PostMapping("updateRectifyAttendance")
    public ResponseEntity<RES_updateRectifyAttendance> updateRectifyAttendance(@RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.updateRectifyAttendance(rectifyAttendanceDTO));
    }

    /**
     * 출,퇴근 기록 정정요청목록 조회
     **/
    @PostMapping("readRectifyAttendanceList")
    public ResponseEntity<RES_readRectifyAttendanceList> readRectifyAttendanceList() {
        return ResponseEntity.ok(attendanceService.readRectifyAttendanceList());
    }

    /**
     * 출,퇴근 기록 정정요청상세 조회
     **/
    @PostMapping("readRectifyAttendance")
    public ResponseEntity<RES_readRectifyAttendance> readRectifyAttendance(@RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.readRectifyAttendance(rectifyAttendanceDTO));
    }

    /**
     * 출,퇴근 기록 정정요청 삭제
     **/
    @PostMapping("deleteRectifyAttendance")
    public ResponseEntity<RES_deleteRectifyAttendance> deleteRectifyAttendance(@RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.deleteRectifyAttendance(rectifyAttendanceDTO));
    }

    /**
     * 승인해야할 출,퇴근 정정요청목록 조회
     **/
    @PostMapping("readRectifyAttendanceListForApprove")
    public ResponseEntity<RES_readRectifyAttendanceList> readRectifyAttendanceListForApprove() {
        return ResponseEntity.ok(attendanceService.readRectifyAttendanceListForApprove());
    }

    /**
     * 출,퇴근 기록 정정요청 승인
     **/
    @PostMapping("approveRectifyAttendance")
    public ResponseEntity<RES_approveRectifyAttendance> approveRectifyAttendance(@RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.approveRectifyAttendance(rectifyAttendanceDTO));
    }

    /**
     * 출,퇴근 기록 요약정보 조회
     **/
    @PostMapping("readAttendanceSummary")
    public ResponseEntity<RES_readAttendanceSummary> readAttendanceSummary() {
        return ResponseEntity.ok(attendanceService.readAttendanceSummary());
    }

    /**
     * 개인 출,퇴근 당일정보 조회
     **/
    @PostMapping("readAttendanceToday")
    public ResponseEntity<RES_readAttendance> readAttendanceToday() {
        return ResponseEntity.ok(attendanceService.readAttendanceToday());
    }
}
