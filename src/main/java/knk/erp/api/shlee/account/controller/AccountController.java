package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.SignUp_MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.SignUp_MemberDTO_RES;
import knk.erp.api.shlee.common.dto.TokenDto;
import knk.erp.api.shlee.common.dto.TokenRequestDto;
import knk.erp.api.shlee.account.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LoginService loginService;

    @GetMapping("/hello")
    public String hi(){
        return "HI";
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUp_MemberDTO_RES> signup(@RequestBody SignUp_MemberDTO_REQ signUpMemberDTOReq){
        return ResponseEntity.ok(loginService.signup(signUpMemberDTOReq));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody SignUp_MemberDTO_REQ signUpMemberDTOReq){
        logger.info("이상훈 테스트__ 요청 정보: {}", signUpMemberDTOReq.toString());
        return ResponseEntity.ok(loginService.login(signUpMemberDTOReq));
    }

    // Token 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(loginService.reissue(tokenRequestDto));
    }
}