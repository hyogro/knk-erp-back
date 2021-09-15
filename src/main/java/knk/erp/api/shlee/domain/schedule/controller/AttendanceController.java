package knk.erp.api.shlee.domain.schedule.controller;

import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.schedule.dto.Attendance.*;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.domain.schedule.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    public ResponseEntity<ResponseData> onWork(@RequestBody String uuid) {
        attendanceService.onWork(uuid);

        knk.erp.api.shlee.common.dto.ResponseCM responseCM = knk.erp.api.shlee.common.dto.ResponseCM
                .builder()
                .responseCode(ResponseCode.ON_WORK_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 퇴근 기록
     **/
    @PostMapping("/offWork")
    public ResponseEntity<ResponseData> offWork() {
        attendanceService.offWork();

        knk.erp.api.shlee.common.dto.ResponseCM responseCM = knk.erp.api.shlee.common.dto.ResponseCM
                .builder()
                .responseCode(ResponseCode.OFF_WORK_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 출,퇴근 기록목록 조회
     **/
    @GetMapping("")
    public ResponseEntity<ResponseCMDL> readAttendanceList(@RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(attendanceService.readAttendanceList(LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    /**
     * 출,퇴근 기록 조회
     **/
    @GetMapping("/{aid}")
    public ResponseEntity<ResponseCMD> readAttendance(@PathVariable("aid") Long aid) {
        return ResponseEntity.ok(attendanceService.readAttendance(aid));
    }

    /**
     * 출,퇴근 기록 정정 요청
     **/
    @PostMapping("/rectify")
    public ResponseEntity<ResponseCM> createRectifyAttendance(@RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.createRectifyAttendance(rectifyAttendanceDTO));
    }

    /**
     * 퇴근 기록 정정 요청
     **/
    @PostMapping("/rectify/{aid}")
    public ResponseEntity<ResponseCM> updateRectifyAttendance(@PathVariable("aid") Long aid, @RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        return ResponseEntity.ok(attendanceService.updateRectifyAttendance(aid, rectifyAttendanceDTO));
    }

    /**
     * 출,퇴근 기록 정정요청목록 조회
     **/
    @GetMapping("/rectify")
    public ResponseEntity<ResponseCMDL> readRectifyAttendanceList() {
        return ResponseEntity.ok(attendanceService.readRectifyAttendanceList());
    }

    /**
     * 출,퇴근 기록 정정요청상세 조회
     **/
    @GetMapping("/rectify/{rid}")
    public ResponseEntity<ResponseCMD> readRectifyAttendance(@PathVariable("rid") Long rid) {
        return ResponseEntity.ok(attendanceService.readRectifyAttendance(rid));
    }

    /**
     * 출,퇴근 기록 정정요청 삭제
     **/
    @DeleteMapping("/rectify/{rid}")
    public ResponseEntity<ResponseCM> deleteRectifyAttendance(@PathVariable Long rid) {
        return ResponseEntity.ok(attendanceService.deleteRectifyAttendance(rid));
    }

    /**
     * 승인해야할 출,퇴근 정정요청목록 조회
     **/
    @GetMapping("/rectify/approve")
    public ResponseEntity<ResponseCMDL> readRectifyAttendanceListForApprove() {
        return ResponseEntity.ok(attendanceService.readRectifyAttendanceListForApprove());
    }

    /**
     * 출,퇴근 기록 정정요청 승인
     **/
    @PutMapping("/rectify/approve/{rid}")
    public ResponseEntity<ResponseCM> approveRectifyAttendance(@PathVariable Long rid) {
        return ResponseEntity.ok(attendanceService.approveRectifyAttendance(rid));
    }

    /**
     * 출,퇴근 기록 요약정보 조회
     **/
    @GetMapping("summary")
    public ResponseEntity<ResponseCMD> readAttendanceSummary() {
        return ResponseEntity.ok(attendanceService.readAttendanceSummary());
    }

    /**
     * 개인 출,퇴근 당일정보 조회
     **/
    @GetMapping("today")
    public ResponseEntity<ResponseCMD> readAttendanceToday() {
        return ResponseEntity.ok(attendanceService.readAttendanceToday());
    }

    /**
     * uuid 중복되는 출,퇴근 기록목록 조회
     **/
    @GetMapping("/duplicate")
    public ResponseEntity<ResponseCMDL> readDuplicateAttendanceList(@RequestParam("date") String date) {
        return ResponseEntity.ok(attendanceService.readDuplicateAttendanceList(LocalDate.parse(date)));
    }
}
