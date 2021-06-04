package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.SignUp_MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.SignUp_MemberDTO_RES;
import knk.erp.api.shlee.common.dto.TokenDto;
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
    public ResponseEntity<SignUp_MemberDTO_RES> signup(@RequestBody SignUp_MemberDTO_REQ signUpMemberDTOReq){
        return ResponseEntity.ok(loginService.signup(signUpMemberDTOReq));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody SignUp_MemberDTO_REQ signUpMemberDTOReq){
        return ResponseEntity.ok(loginService.login(signUpMemberDTOReq));
    }

}