package knk.erp.api.shlee.domain.schedule.controller;

import knk.erp.api.shlee.domain.schedule.dto.Vacation.*;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.domain.schedule.responseEntity.vacation.*;
import knk.erp.api.shlee.domain.schedule.service.VacationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public ResponseEntity<ResponseCM> createAddVacation(@RequestBody AddVacationDTO addVacationDTO){
        return ResponseEntity.ok(vacationService.createAddVacation(addVacationDTO));
    }

    /**
     * 추가휴가 전체 목록조회
     **/
    @GetMapping("/add/list")
    public ResponseEntity<ResponseCMDL> readAddVacationAllList(){
        return ResponseEntity.ok(vacationService.readAddVacationAllList());
    }


    /**
     * 추가휴가 특정 회원 목록조회
     **/
    @GetMapping("/add/list/{mid}")
    public ResponseEntity<ResponseCMDL> readAddVacationList(@PathVariable("mid") String memberId){
        return ResponseEntity.ok(vacationService.readAddVacationList(memberId));
    }

    /**
     * 추가휴가 상세조회
     **/
    @GetMapping("/add/{avid}")
    public ResponseEntity<ResponseCMD> readAddVacationList(@PathVariable("avid") Long avid){
        return ResponseEntity.ok(vacationService.readAddVacationDetail(avid));
    }

    /**
     * 추가휴가 삭제
     **/
    @DeleteMapping("/add/{avid}")
    public ResponseEntity<ResponseCM> deleteAddVacation(@PathVariable("avid") Long avid){
        return ResponseEntity.ok(vacationService.deleteAddVacation(avid));
    }



    //휴가정보 조회
    @GetMapping("info/{mid}")
    public ResponseEntity<ResponseCMD> readVacationInfo(@PathVariable("mid") String memberId){
        return ResponseEntity.ok(vacationService.readVacationInfo(memberId));
    }


    /**
     * 휴가 생성
     **/
    @PostMapping("")
    public ResponseEntity<ResponseCM> createVacation(@RequestBody VacationDTO vacationDTO) {
        return ResponseEntity.ok(vacationService.createVacation(vacationDTO));
    }

    /**
     * 휴가 목록 읽기
     **/
    @GetMapping("")
    public ResponseEntity<ResponseCMDL> readVacationList() {
        return ResponseEntity.ok(vacationService.readVacationList());
    }

    /**
     * 휴가 목록 읽기
     **/
    @GetMapping("/all")
    public ResponseEntity<ResponseCMDL> readAllVacationList(@RequestParam("startDate") String startDate,
                                                            @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(vacationService.readAllVacationList(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate)));
    }


    /**
     * 휴가 상세 읽기
     **/
    @GetMapping("/{vid}")
    public ResponseEntity<ResponseCMD> readVacation(@PathVariable("vid") Long vid) {
        return ResponseEntity.ok(vacationService.readVacationDetail(vid));
    }

    /**
     * 휴가 삭제
     **/
    @DeleteMapping("/{vid}")
    public ResponseEntity<ResponseCM> deleteVacation(@PathVariable("vid") Long vid) {
        return ResponseEntity.ok(vacationService.deleteVacation(vid));
    }

    /**
     * 승인했거나, 거절했었던 휴가목록 읽기
     **/
    @GetMapping("/approve/history")
    public ResponseEntity<ResponseCMDL> readVacationListForManage(@RequestParam("startDate") String startDate,
                                                                  @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(vacationService.readVacationListForManage(LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    /**
     * 승인해야 할 휴가목록 읽기
     **/
    @GetMapping("/approve")
    public ResponseEntity<ResponseCMDL> readVacationListForApprove() {
        return ResponseEntity.ok(vacationService.readVacationListForApprove());
    }

    /**
     * 휴가 승인
     **/
    @PutMapping("/approve/{vid}")
    public ResponseEntity<ResponseCM> approveVacation(@PathVariable Long vid) {
        return ResponseEntity.ok(vacationService.approveVacation(vid));
    }

    /**
     * 휴가 거절
     **/
    @PutMapping("/reject/{vid}")
    public ResponseEntity<ResponseCM> rejectVacation(@PathVariable Long vid , @RequestBody REQ_rejectVacation reject) {
        return ResponseEntity.ok(vacationService.rejectVacation(vid, reject));
    }

}
