package knk.erp.api.shlee.domain.Fixtures.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.Fixtures.dto.*;
import knk.erp.api.shlee.domain.Fixtures.service.FixturesFormService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseData> createFixturesForm(@RequestBody FixturesFormDTO_REQ fixturesFormDTOReq){
        fixturesFormService.createFixturesForm(fixturesFormDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_FIXTURES_FORM_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 내 비품 요청 목록 읽기
    @GetMapping("")
    public ResponseEntity<ResponseData> readFixturesFormList(){
        List<Read_FixturesFormDTO> myFixtures = fixturesFormService.readFixturesFormList();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_MY_FIXTURES_FORM_SUCCESS)
                .data(myFixtures)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 비품 요청서 상세 보기
    @GetMapping("/{fixturesFormId}")
    public ResponseEntity<ResponseData> readDetailFixturesForm(@PathVariable Long fixturesFormId){
        ReadDetail_FixturesFormDTO readDetailFixturesFormDTO = fixturesFormService.readDetailFixturesForm(fixturesFormId);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_DETAIL_FIXTURES_FORM_SUCCESS)
                .data(readDetailFixturesFormDTO)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 내가 쓴 비품 요청서 수정
    @PutMapping("/{fixturesFormId}")
    public ResponseEntity<ResponseData> updateFixturesForm(@PathVariable Long fixturesFormId,
                                                                         @RequestBody Update_FixturesFormDTO_REQ updateFixturesFormDTOReq){
        fixturesFormService.updateFixturesForm(fixturesFormId, updateFixturesFormDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.UPDATE_MY_FIXTURES_FORM_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 내가 쓴 비품 요청서 삭제
    @DeleteMapping("/{fixturesFormId}")
    public ResponseEntity<ResponseData> deleteFixturesForm(@PathVariable Long fixturesFormId){
        fixturesFormService.deleteFixturesForm(fixturesFormId);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_MY_FIXTURES_FORM_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }


    // 비품 요청서 전체/미처리/처리완료 목록 보기(ROLE_LVL3 이상, ROLE_MANAGE)
    @GetMapping("/listAll")
    public ResponseEntity<ResponseData> readAllFixturesForm(Pageable pageable, @RequestParam String searchType){
        ReadAll_FixturesFormDTO_RES readAllFixturesFormDTORes = fixturesFormService.readAllFixturesForm(pageable, searchType);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ALL_FIXTURES_FORM_SUCCESS)
                .data(readAllFixturesFormDTORes)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 비품 승인 및 거절(ROLE_LVL3 이상)
    @PutMapping("/confirm/{fixturesFormId}")
    public ResponseEntity<ResponseData> confirmFixtures(@PathVariable Long fixturesFormId,
                                                                   @RequestBody Confirm_FixturesDTO confirmFixturesDTO){
        fixturesFormService.confirmFixtures(fixturesFormId, confirmFixturesDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.CONFIRM_FIXTURES_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // PUT 품목에 대한 구매 여부 변경(ROLE_LVL3 이상, ROLE_MANAGE)
    @PutMapping("/purchase/{fixturesFormId}")
    public ResponseEntity<ResponseData> purchaseFixtures(@PathVariable Long fixturesFormId,
                                                                     @RequestBody Purchase_FixturesDTO purchaseFixturesDTO){
        fixturesFormService.purchaseFixtures(fixturesFormId, purchaseFixturesDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.PURCHASE_FIXTURES_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

}
