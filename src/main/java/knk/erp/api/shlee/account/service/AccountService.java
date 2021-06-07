package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.account.Read_AccountDTO_RES;
import knk.erp.api.shlee.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.account.SignUp_MemberDTO_RES;
import knk.erp.api.shlee.account.dto.account.Login_TokenDTO_RES;
import knk.erp.api.shlee.account.dto.member.Read_MemberDTO_REQ;
import knk.erp.api.shlee.account.entity.*;
import knk.erp.api.shlee.account.util.AccountUtil;
import knk.erp.api.shlee.common.dto.TokenDto;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final AccountUtil accountUtil;

    // 회원 가입
    @Transactional
    public SignUp_MemberDTO_RES signup(MemberDTO_REQ MemberDTOReq){

        Department department = departmentRepository.getOne(MemberDTOReq.getDepartmentId());

        if(memberRepository.existsByMemberId(MemberDTOReq.getMemberId())) {
            return new SignUp_MemberDTO_RES("SU003", "이미 가입된 ID 입니다.");
        }
        Member member = MemberDTOReq.toMember(passwordEncoder);

        member.setDepartment(department);

        try {
            memberRepository.save(member);
            department.getMemberList().add(member);
            departmentRepository.save(department);
            return new SignUp_MemberDTO_RES("SU001");
        }catch (Exception e){
            return new SignUp_MemberDTO_RES("SU002", e.getMessage());
        }
    }

    // 로그인 및 Access Token 발급
    @Transactional
    public Login_TokenDTO_RES login(MemberDTO_REQ MemberDTOReq){

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = MemberDTOReq.toAuthentication();

        try{
            // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
            //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByMemberId 메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            // 4. 토큰 발급
            return new Login_TokenDTO_RES("LI001", tokenDto);
        }catch(Exception e){
            return new Login_TokenDTO_RES("LI002", e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Read_AccountDTO_RES readMember(){
        try{
            List<Member> memberList = memberRepository.findAllByDeletedIsFalse();
            return new Read_AccountDTO_RES("RA001", accountUtil.getMemberList(memberList));
        }catch(Exception e){
            return new Read_AccountDTO_RES("RA002", e.getMessage());
        }
    }
}
