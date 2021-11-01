package knk.erp.api.shlee.domain.account.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.account.dto.member.Read_MemberDTO;
import knk.erp.api.shlee.domain.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.my.*;
import knk.erp.api.shlee.domain.account.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyPageController {
    private final MyPageService myPageService;

    // 회원 본인 정보 불러오기
    @GetMapping("")
    public ResponseEntity<ResponseData> getMyInfo(){
        Read_MemberDTO readMemberDTO = myPageService.getMyInfo();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_MY_INFO_SUCCESS)
                .data(readMemberDTO)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);

    }

    // 회원 본인 정보 수정
    @PutMapping("")
    public ResponseEntity<ResponseData> updateSelf(@RequestBody Update_SelfDTO updateSelfDTO){
        myPageService.updateSelf(updateSelfDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.UPDATE_MY_INFO_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 본인 포상휴가 분단위
    @GetMapping("/getMyVacation")
    public ResponseEntity<ResponseData> getMyVacation(){
        int vacation = myPageService.getMyVacation();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_MY_VACATION_SUCCESS)
                .data(vacation)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }
}
