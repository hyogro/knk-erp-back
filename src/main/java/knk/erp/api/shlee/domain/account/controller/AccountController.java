package knk.erp.api.shlee.domain.account.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseData> signup(@RequestBody MemberDTO_REQ memberDTOReq){
        accountService.signup(memberDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.SIGNUP_USER_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Login_TokenDTO_RES> login(@RequestBody MemberDTO_REQ memberDTOReq){
        return ResponseEntity.ok(accountService.login(memberDTOReq));
    }

    // 회원 목록 읽어오기
    @GetMapping("")
    public ResponseEntity<Read_AccountDTO_RES> readMember(){
        return ResponseEntity.ok(accountService.readMember());
    }

    // 회원 정보 상세보기
    @GetMapping("/{memberId}")
    public ResponseEntity<ReadDetail_AccountDTO_RES> readMemberDetail(@PathVariable String memberId){
        return ResponseEntity.ok(accountService.readMemberDetail(memberId));
    }

    //회원 정보 수정
    @PutMapping("/{memberId}")
    public ResponseEntity<Update_AccountDTO_RES> updateMember(@PathVariable String memberId,
                                                              @RequestBody Update_AccountDTO_REQ updateAccountDTOReq){
        return ResponseEntity.ok(accountService.updateMember(memberId, updateAccountDTOReq));
    }

    // 회원 정보 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Delete_AccountDTO_RES> deleteMember(@PathVariable String memberId){
        return ResponseEntity.ok(accountService.deleteMember(memberId));
    }

    // 중복 ID 체크
    @PostMapping("/checkId")
    public ResponseEntity<Check_existMemberId_RES> checkId(@RequestBody Check_existMemberIdDTO checkExistMemberIdDTO){
        return ResponseEntity.ok(accountService.checkId(checkExistMemberIdDTO));
    }
}