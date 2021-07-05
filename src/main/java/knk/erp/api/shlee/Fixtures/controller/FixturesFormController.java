package knk.erp.api.shlee.Fixtures.controller;

import knk.erp.api.shlee.Fixtures.dto.*;
import knk.erp.api.shlee.Fixtures.service.FixturesFormService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/fixtures")
public class FixturesFormController {
    FixturesFormService fixturesFormService;

    public FixturesFormController(FixturesFormService fixturesFormService) {
        this.fixturesFormService = fixturesFormService;
    }

    // 비품 요청 생성
    @PostMapping("")
    public ResponseEntity<Create_FixturesFormDTO_RES> createFixturesForm(@RequestBody FixturesFormDTO_REQ fixturesFormDTOReq){
        return ResponseEntity.ok(fixturesFormService.createFixturesForm(fixturesFormDTOReq));
    }

    // 내 비품 요청 목록 읽기
    @GetMapping("")
    public ResponseEntity<Read_FixturesFormDTO_RES> readFixturesFormList(){
        return ResponseEntity.ok(fixturesFormService.readFixturesFormList());
    }

    // 비품 요청서 상세 보기
    @GetMapping("/{fixturesFormId}")
    public ResponseEntity<ReadDetail_FixturesFormDTO_RES> readDetailFixturesForm(@PathVariable Long fixturesFormId){
        return ResponseEntity.ok(fixturesFormService.readDetailFixturesForm(fixturesFormId));
    }

    // 내가 쓴 비품 요청서 수정
    @PutMapping("/{fixturesFormId}")
    public ResponseEntity<Update_FixturesFormDTO_RES> updateFixturesForm(@PathVariable Long fixturesFormId,
                                                                         @RequestBody Update_FixturesFormDTO_REQ updateFixturesFormDTOReq){
        return ResponseEntity.ok(fixturesFormService.updateFixturesForm(fixturesFormId, updateFixturesFormDTOReq));
    }

    // 내가 쓴 비품 요청서 삭제
    @DeleteMapping("/{fixturesFormId}")
    public ResponseEntity<Delete_FixturesFormDTO_RES> deleteFixturesForm(@PathVariable Long fixturesFormId){
        return ResponseEntity.ok(fixturesFormService.deleteFixturesForm(fixturesFormId));
    }


    // 비품 요청서 전체/미처리/처리완료 목록 보기(ROLE_LVL3 이상, ROLE_MANAGE)
    @GetMapping("/listAll")
    public ResponseEntity<ReadAll_FixturesFormDTO_RES> readAllFixturesForm(Pageable pageable,
                                                                           @RequestParam String searchType){
        return ResponseEntity.ok(fixturesFormService.readAllFixturesForm(pageable, searchType));
    }

    // 비품 승인 및 거절(ROLE_LVL3 이상)
    @PutMapping("/confirm/{fixturesFormId}")
    public ResponseEntity<Confrim_FixturesDTO_RES> confirmFixtures(@PathVariable Long fixturesFormId,
                                                                   @RequestBody Confirm_FixturesDTO confirmFixturesDTO){
        return ResponseEntity.ok(fixturesFormService.confirmFixtures(fixturesFormId, confirmFixturesDTO));
    }

    // PUT 품목에 대한 구매 여부 변경(ROLE_LVL3 이상, ROLE_MANAGE)
    @PutMapping("/purchase/{fixturesFormId}")
    public ResponseEntity<Purchase_FixturesDTO_RES> purchaseFixtures(@PathVariable Long fixturesFormId,
                                                                     @RequestBody Purchase_FixturesDTO purchaseFixturesDTO){
        return ResponseEntity.ok(fixturesFormService.purchaseFixtures(fixturesFormId, purchaseFixturesDTO));
    }

}
