package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.SignUp_MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.SignUp_MemberDTO_RES;
import knk.erp.api.shlee.account.dto.TokenDto;
import knk.erp.api.shlee.account.dto.TokenRequestDto;
import knk.erp.api.shlee.account.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/account")
public class AccountController {
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<SignUp_MemberDTO_RES> signup(@RequestBody SignUp_MemberDTO_REQ signUpMemberDTOReq){
        return ResponseEntity.ok(loginService.signup(signUpMemberDTOReq));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody SignUp_MemberDTO_REQ signUpMemberDTOReq){
        return ResponseEntity.ok(loginService.login(signUpMemberDTOReq));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(loginService.reissue(tokenRequestDto));
    }
}