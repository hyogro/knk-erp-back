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

    // 회원 정보 읽어오기
    @PostMapping("/readMember")
    public ResponseEntity<Read_AccountDTO_RES> readMember(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(accountService.readMember(departmentDTOReq));
    }

    // 회원 정보 수정
    @PostMapping("/updateMember")
    public ResponseEntity<Update_AccountDTO_RES> updateMember(@RequestBody Update_AccountDTO_REQ updateAccountDTOReq){
        return ResponseEntity.ok(accountService.updateMember(updateAccountDTOReq));
    }

    // 회원 정보 삭제
    @PostMapping("/deleteMember")
    public ResponseEntity<Delete_AccountDTO_RES> deleteMember(@RequestBody MemberDTO_REQ memberDTOReq){
        return ResponseEntity.ok(accountService.deleteMember(memberDTOReq));
    }

}