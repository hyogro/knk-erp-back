package knk.erp.api.shlee.Fixtures.controller;

import knk.erp.api.shlee.Fixtures.dto.*;
import knk.erp.api.shlee.Fixtures.service.FixturesFormService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //PUT 내가쓴거 수정하기
    @PutMapping("/{fixturesFormId}")
    public ResponseEntity<Update_FixturesFormDTO_RES> updateFixturesForm(@PathVariable Long fixturesFormId,
                                                                         @RequestBody Update_FixturesFormDTO_REQ updateFixturesFormDTOReq){
        return ResponseEntity.ok(fixturesFormService.updateFixturesForm(fixturesFormId, updateFixturesFormDTOReq));
    }

    //DELETE 내가쓴거 삭제하기 isDeleted = true;

    //GET 남이쓴거 목록 가져오기(페이징, 권한)

    //PUT 품목에 대한 승인, 거절 여부 변경

    //PUT 품목에 대한 구매 여부 변경

}
