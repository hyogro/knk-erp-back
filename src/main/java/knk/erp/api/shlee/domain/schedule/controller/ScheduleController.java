package knk.erp.api.shlee.domain.schedule.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.schedule.dto.Schedule.*;
import knk.erp.api.shlee.domain.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //일정 생성
    @PostMapping("")
    public ResponseEntity<ResponseData> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.createSchedule(scheduleDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.APPROVE_RECTIFY_ATTENDANCE_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    //일정 목록 읽기
    @GetMapping("")
    public ResponseEntity<ResponseData> readScheduleList(@RequestParam("viewOption") String viewOption,
                                                         @RequestParam("startDate") String startDate,
                                                         @RequestParam("endDate") String endDate) {
        List<Object> data = scheduleService.readScheduleList(viewOption, LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_SCHEDULE_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    //일정 상세 읽기
    @GetMapping("/{sid}")
    public ResponseEntity<ResponseData> readScheduleDetail(@PathVariable("sid") Long sid) {
        ScheduleDetailData data = scheduleService.readScheduleDetail(sid);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_SCHEDULE_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    //일정 수정
    @PutMapping("/{sid}")
    public ResponseEntity<ResponseData> updateSchedule(@PathVariable("sid") Long sid, @RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.updateSchedule(sid, scheduleDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.UPDATE_SCHEDULE_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);

    }

    //일정 삭제
    @DeleteMapping("/{sid}")
    public ResponseEntity<ResponseData> deleteSchedule(@PathVariable("sid") Long sid) {
        scheduleService.deleteSchedule(sid);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_SCHEDULE_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    //기념일 목록 읽기
    @GetMapping("/anniversary")
    public ResponseEntity<ResponseData> readAnniversaryList(@RequestParam("startDate") String startDate,
                                                         @RequestParam("endDate") String endDate) {
        List<Object> data = scheduleService.readAnniversaryList(LocalDateTime.parse(startDate).toLocalDate(), LocalDateTime.parse(endDate).toLocalDate());

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_SCHEDULE_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }
}
