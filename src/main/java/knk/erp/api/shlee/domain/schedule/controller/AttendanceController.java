package knk.erp.api.shlee.domain.schedule.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.schedule.dto.Attendance.*;
import knk.erp.api.shlee.domain.schedule.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    AttendanceService attendanceService;

    AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * 출근 기록
     * request: uuid
     * response: code
     **/
    @PostMapping("/onWork")
    public ResponseEntity<ResponseData> onWork(@RequestBody String uuid) {
        attendanceService.onWork(uuid);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.ON_WORK_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 퇴근 기록
     * response: code
     **/
    @PostMapping("/offWork")
    public ResponseEntity<ResponseData> offWork() {
        attendanceService.offWork();

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.OFF_WORK_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 날짜 범위로 본인의
     * 출,퇴근 기록목록 조회
     * request: 시작, 종료일
     * response: 출근정보 목록
     **/
    @GetMapping("/list")
    public ResponseEntity<ResponseData> readAttendanceList(@RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate) {
        List<AttendanceDto> attendanceDtoList = attendanceService.readAttendanceList(LocalDate.parse(startDate), LocalDate.parse(endDate));

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ATTENDANCE_SUCCESS)
                .data(attendanceDtoList)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 출,퇴근 기록 조회
     **/
    @GetMapping("/{aid}")
    public ResponseEntity<ResponseData> readAttendance(@PathVariable("aid") Long aid) {
        AttendanceDto attendanceDto = attendanceService.readAttendance(aid);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ATTENDANCE_SUCCESS)
                .data(attendanceDto)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 정정요청 생성
     * 미 출근시 정정요청 생성
     **/
    @PostMapping("/rectify")
    public ResponseEntity<ResponseData> createRectifyAttendance(@RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        attendanceService.createRectifyAttendance(rectifyAttendanceDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_RECTIFY_ATTENDANCE_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 정정요청 생성
     * 기존 출근기록 정정요청 생성
     **/
    @PostMapping("/rectify/{aid}")
    public ResponseEntity<ResponseData> updateRectifyAttendance(@PathVariable("aid") Long aid, @RequestBody RectifyAttendanceDTO rectifyAttendanceDTO) {
        attendanceService.updateRectifyAttendance(aid, rectifyAttendanceDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_RECTIFY_ATTENDANCE_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 출,퇴근 기록 정정요청목록 조회
     **/
    @GetMapping("/rectify")
    public ResponseEntity<ResponseData> readRectifyAttendanceList() {
        List<Object> data = attendanceService.readRectifyAttendanceList();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_RECTIFY_ATTENDANCE_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 출,퇴근 기록 정정요청상세 조회
     **/
    @GetMapping("/rectify/{rid}")
    public ResponseEntity<ResponseData> readRectifyAttendance(@PathVariable("rid") Long rid) {
        RectifyAttendanceDetailData data = attendanceService.readRectifyAttendance(rid);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_RECTIFY_ATTENDANCE_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 출,퇴근 기록 정정요청 삭제
     **/
    @DeleteMapping("/rectify/{rid}")
    public ResponseEntity<ResponseData> deleteRectifyAttendance(@PathVariable Long rid) {
        attendanceService.deleteRectifyAttendance(rid);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_RECTIFY_ATTENDANCE_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 승인해야할 출,퇴근 정정요청목록 조회
     **/
    @GetMapping("/rectify/approve")
    public ResponseEntity<ResponseData> readRectifyAttendanceListForApprove() {
        List<Object> data = attendanceService.readRectifyAttendanceListForApprove();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_RECTIFY_ATTENDANCE_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 출,퇴근 기록 정정요청 승인
     **/
    @PutMapping("/rectify/approve/{rid}")
    public ResponseEntity<ResponseData> approveRectifyAttendance(@PathVariable Long rid) {
        attendanceService.approveRectifyAttendance(rid);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.APPROVE_RECTIFY_ATTENDANCE_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }


    /**
     * 출,퇴근 기록 요약정보 조회
     **/
    @GetMapping("/summary")
    public ResponseEntity<ResponseData> readAttendanceSummary() {
        AttendanceSummaryDTO data = attendanceService.readAttendanceSummary();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ATTENDANCE_SUMMARY_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 개인 출,퇴근 당일정보 조회
     **/
    @GetMapping("/today")
    public ResponseEntity<ResponseData> readAttendanceToday() {
        AttendanceDto data = attendanceService.readAttendanceToday();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ATTENDANCE_TODAY_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);

    }

    /**
     * uuid 중복되는 출,퇴근 기록목록 조회
     **/
    @GetMapping("/duplicate")
    public ResponseEntity<ResponseData> readDuplicateAttendanceList(@RequestParam("date") String date) {
        List<Object> data = attendanceService.readDuplicateAttendanceList(LocalDate.parse(date));

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ATTENDANCE_DUPLICATE_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }
}
