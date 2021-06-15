package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.account.*;
import knk.erp.api.shlee.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUp_MemberDTO_RES> signup(@RequestBody MemberDTO_REQ memberDTOReq){
        return ResponseEntity.ok(accountService.signup(memberDTOReq));
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

   // 회원 정보 수정
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

}