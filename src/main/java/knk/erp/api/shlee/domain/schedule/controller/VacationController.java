package knk.erp.api.shlee.domain.schedule.controller;

import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.schedule.dto.Vacation.*;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.domain.schedule.responseEntity.vacation.*;
import knk.erp.api.shlee.domain.schedule.service.VacationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/vacation")
public class VacationController {
    VacationService vacationService;

    VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    /**
     * 추가휴가 생성
     **/
    @PostMapping("/add")
    public ResponseEntity<ResponseData> createAddVacation(@RequestBody AddVacationDTO addVacationDTO){
        vacationService.createAddVacation(addVacationDTO);

        knk.erp.api.shlee.common.dto.ResponseCM responseCM = knk.erp.api.shlee.common.dto.ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_ADD_VACATION_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 추가휴가 전체 목록조회
     **/
    @GetMapping("/add/list")
    public ResponseEntity<ResponseData> readAddVacationAllList(){
        List<Object> data = vacationService.readAddVacationAllList();

        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ADD_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }


    /**
     * 추가휴가 특정 회원 목록조회
     **/
    @GetMapping("/add/list/{mid}")
    public ResponseEntity<ResponseData> readAddVacationList(@PathVariable("mid") String memberId){
        List<Object> data = vacationService.readAddVacationList(memberId);

        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ADD_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 추가휴가 상세조회
     **/
    @GetMapping("/add/{avid}")
    public ResponseEntity<ResponseData> readAddVacationList(@PathVariable("avid") Long avid){
        AddVacationDetailData data = vacationService.readAddVacationDetail(avid);

        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ADD_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 추가휴가 삭제
     **/
    @DeleteMapping("/add/{avid}")
    public ResponseEntity<ResponseData> deleteAddVacation(@PathVariable("avid") Long avid){
        vacationService.deleteAddVacation(avid);

        knk.erp.api.shlee.common.dto.ResponseCM responseCM = knk.erp.api.shlee.common.dto.ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_ADD_VACATION_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }



    //휴가정보 조회
    @GetMapping("info/{mid}")
    public ResponseEntity<ResponseData> readVacationInfo(@PathVariable("mid") String memberId){
        VacationInfo data = vacationService.readVacationInfo(memberId);

        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }


    /**
     * 휴가 생성
     **/
    @PostMapping("")
    public ResponseEntity<ResponseData> createVacation(@RequestBody VacationDTO vacationDTO) {
        vacationService.createVacation(vacationDTO);

        knk.erp.api.shlee.common.dto.ResponseCM responseCM = knk.erp.api.shlee.common.dto.ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_VACATION_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 휴가 목록 읽기
     **/
    @GetMapping("")
    public ResponseEntity<ResponseData> readVacationList() {
        List<Object> data = vacationService.readVacationList();

        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 휴가 목록 읽기
     **/
    @GetMapping("/all")
    public ResponseEntity<ResponseData> readAllVacationList(@RequestParam("startDate") String startDate,
                                                            @RequestParam("endDate") String endDate) {

        List<Object> data = vacationService.readAllVacationList(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));

        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }


    /**
     * 휴가 상세 읽기
     **/
    @GetMapping("/{vid}")
    public ResponseEntity<ResponseData> readVacation(@PathVariable("vid") Long vid) {

        VacationDetailData data = vacationService.readVacationDetail(vid);

        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 휴가 삭제
     **/
    @DeleteMapping("/{vid}")
    public ResponseEntity<ResponseData> deleteVacation(@PathVariable("vid") Long vid) {
        vacationService.deleteVacation(vid);

        knk.erp.api.shlee.common.dto.ResponseCM responseCM = knk.erp.api.shlee.common.dto.ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_VACATION_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 승인했거나, 거절했었던 휴가목록 읽기
     **/
    @GetMapping("/approve/history")
    public ResponseEntity<ResponseData> readVacationListForManage(@RequestParam("startDate") String startDate,
                                                                  @RequestParam("endDate") String endDate) {
        List<Object> data = vacationService.readVacationListForManage(LocalDate.parse(startDate), LocalDate.parse(endDate));


        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 승인해야 할 휴가목록 읽기
     **/
    @GetMapping("/approve")
    public ResponseEntity<ResponseData> readVacationListForApprove() {
        List<Object> data = vacationService.readVacationListForApprove();

        knk.erp.api.shlee.common.dto.ResponseCMD responseCMD = knk.erp.api.shlee.common.dto.ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_VACATION_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    /**
     * 휴가 승인
     **/
    @PutMapping("/approve/{vid}")
    public ResponseEntity<ResponseData> approveVacation(@PathVariable Long vid) {
        vacationService.approveVacation(vid);

        knk.erp.api.shlee.common.dto.ResponseCM responseCM = knk.erp.api.shlee.common.dto.ResponseCM
                .builder()
                .responseCode(ResponseCode.APPROVE_VACATION_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    /**
     * 휴가 거절
     **/
    @PutMapping("/reject/{vid}")
    public ResponseEntity<ResponseData> rejectVacation(@PathVariable Long vid , @RequestBody REQ_rejectVacation reject) {
        vacationService.rejectVacation(vid, reject);

        knk.erp.api.shlee.common.dto.ResponseCM responseCM = knk.erp.api.shlee.common.dto.ResponseCM
                .builder()
                .responseCode(ResponseCode.REJECT_VACATION_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

}
