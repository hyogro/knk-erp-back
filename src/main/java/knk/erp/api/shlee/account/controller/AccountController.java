package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.login.Login_TokenDTO_RES;
import knk.erp.api.shlee.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.signup.SignUp_MemberDTO_RES;
import knk.erp.api.shlee.account.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final LoginService loginService;

    @PostMapping("/hello")
    public String hi(){
        return "hi";
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUp_MemberDTO_RES> signup(@RequestBody MemberDTO_REQ MemberDTOReq){
        return ResponseEntity.ok(loginService.signup(MemberDTOReq));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Login_TokenDTO_RES> login(@RequestBody MemberDTO_REQ MemberDTOReq){
        return ResponseEntity.ok(loginService.login(MemberDTOReq));
    }

}