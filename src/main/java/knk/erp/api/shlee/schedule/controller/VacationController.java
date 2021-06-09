package knk.erp.api.shlee.schedule.controller;

import knk.erp.api.shlee.schedule.dto.Vacation.*;
import knk.erp.api.shlee.schedule.service.VacationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacation")
public class VacationController {
    VacationService vacationService;

    VacationController(VacationService vacationService){
        this.vacationService = vacationService;
    }

    /** 휴가 생성 **/
    @PostMapping("/createVacation")
    public ResponseEntity<RES_createVacation> createVacation(@RequestBody VacationDTO vacationDTO){
        return ResponseEntity.ok(vacationService.createVacation(vacationDTO));
    }

    /** 휴가 목록 읽기 **/
    @PostMapping("/readVacationList")
    public ResponseEntity<RES_readVacationList> readVacationList(){
        return ResponseEntity.ok(vacationService.readVacationList());
    }

    /**휴가 삭제**/
    @PostMapping("/deleteVacation")
    public ResponseEntity<RES_deleteVacation> deleteVacation(@RequestBody VacationDTO VacationDTO){
        return ResponseEntity.ok(vacationService.deleteVacation(VacationDTO));
    }

    /**메인화면 휴가 목록 읽기**/
    @PostMapping("/readVacationListForApprove")
    public ResponseEntity<RES_readVacationList> readVacationListForApprove(){
        return ResponseEntity.ok(vacationService.readVacationListForApprove());
    }
    /**휴가 승인**/
    @PostMapping("/approveVacation")
    public ResponseEntity<RES_approveVacation> approveVacation(@RequestBody VacationDTO vacationDTO){
        return ResponseEntity.ok(vacationService.approveVacation(vacationDTO));
    }
    /**휴가 거절**/
    @PostMapping("/rejectVacation")
    public ResponseEntity<RES_rejectVacation> rejectVacation(@RequestBody REQ_rejectVacation reject){
        return ResponseEntity.ok(vacationService.rejectVacation(reject));
    }
}
