package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Vacation.*;
import knk.erp.api.shlee.schedule.service.VacationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vacation")
public class VacationController {
    VacationService vacationService;

    VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    /**
     * 휴가 생성
     **/
    @PostMapping("")
    public ResponseEntity<RES_createVacation> createVacation(@RequestBody VacationDTO vacationDTO) {
        return ResponseEntity.ok(vacationService.createVacation(vacationDTO));
    }

    /**
     * 휴가 목록 읽기
     **/
    @GetMapping("")
    public ResponseEntity<RES_readVacationList> readVacationList() {
        return ResponseEntity.ok(vacationService.readVacationList());
    }


    /**
     * 휴가 상세 읽기
     **/
    @GetMapping("/{vid}")
    public ResponseEntity<RES_readVacation> readVacation(@PathVariable("vid") Long vid) {
        return ResponseEntity.ok(vacationService.readVacation(vid));
    }

    /**
     * 휴가 삭제
     **/
    @DeleteMapping("/{vid}")
    public ResponseEntity<RES_deleteVacation> deleteVacation(@PathVariable("vid") Long vid) {
        return ResponseEntity.ok(vacationService.deleteVacation(vid));
    }

    /**
     * 승인해야 할 휴가목록 읽기
     **/
    @GetMapping("/approve")
    public ResponseEntity<RES_readVacationList> readVacationListForApprove() {
        return ResponseEntity.ok(vacationService.readVacationListForApprove());
    }

    /**
     * 휴가 승인
     **/
    @PutMapping("/approve/{vid}")
    public ResponseEntity<RES_approveVacation> approveVacation(@PathVariable Long vid) {
        return ResponseEntity.ok(vacationService.approveVacation(vid));
    }

    /**
     * 휴가 거절
     **/
    @PutMapping("/reject/{vid}")
    public ResponseEntity<RES_rejectVacation> rejectVacation(@PathVariable Long vid , @RequestBody REQ_rejectVacation reject) {
        return ResponseEntity.ok(vacationService.rejectVacation(vid, reject));
    }

    /**
     * 휴가 요약정보 조회
     **/
    @GetMapping("/summary")
    public ResponseEntity<RES_readVacationSummary> rejectVacation() {
        return ResponseEntity.ok(vacationService.readVacationSummary());
    }


}
