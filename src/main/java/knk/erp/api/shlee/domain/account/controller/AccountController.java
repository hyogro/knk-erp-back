package knk.erp.api.shlee.domain.account.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.account.dto.account.*;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseData> signup(@RequestBody @Valid MemberDTO_REQ memberDTOReq){
        accountService.signup(memberDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.SIGNUP_USER_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Login_TokenDTO_RES> login(@RequestBody @Valid LoginDTO loginDTO){
        return ResponseEntity.ok(accountService.login(loginDTO));
    }

    // 회원 목록 읽어오기
    @GetMapping("")
    public ResponseEntity<ResponseData> readMember(){
        List<Read_AccountDTO> accountDTOList = accountService.readMember();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_USER_SUCCESS)
                .data(accountDTOList)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 회원 정보 상세보기
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseData> readMemberDetail(@PathVariable String memberId){
        ReadDetail_AccountDTO readDetailAccountDTO = accountService.readMemberDetail(memberId);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_DETAIL_USER_SUCCESS)
                .data(readDetailAccountDTO)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    //회원 정보 수정
    @PutMapping("/{memberId}")
    public ResponseEntity<ResponseData> updateMember(@PathVariable String memberId,
                                                              @RequestBody @Valid Update_AccountDTO_REQ updateAccountDTOReq){
        accountService.updateMember(memberId, updateAccountDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.UPDATE_USER_INFO_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 회원 정보 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ResponseData> deleteMember(@PathVariable String memberId){
        accountService.deleteMember(memberId);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_USER_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 중복 ID 체크
    @PostMapping("/checkId")
    public ResponseEntity<ResponseData> checkId(@RequestBody Check_existMemberIdDTO checkExistMemberIdDTO){
        boolean check = accountService.checkId(checkExistMemberIdDTO);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.CHECK_OVERLAP_ID_SUCCESS)
                .data(check)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }
}