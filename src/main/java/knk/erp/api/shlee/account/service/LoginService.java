package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.*;
import knk.erp.api.shlee.account.entity.*;
import knk.erp.api.shlee.common.dto.TokenDto;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public List<SignUp_DepartmentDTO> getDepartmentList(){
        List<SignUp_DepartmentDTO> responseData = new ArrayList<>();
        List<Department> departmentList = departmentRepository.findAll();

        for(Department d : departmentList){
            responseData.add(new SignUp_DepartmentDTO(d.getId(), d.getDepartmentName()));

        }
        return responseData;
    }

    // 회원 가입
    @Transactional
    public SignUp_MemberDTO_RES signup(SignUp_MemberDTO_REQ signUpMemberDTOReq){

        Department department = departmentRepository.getOne(signUpMemberDTOReq.getDepartmentId());

        if(memberRepository.existsByMemberId(signUpMemberDTOReq.getMemberId())) {
            throw new RuntimeException("이미 가입되어있는 ID 입니다.");
        }
        Member member = signUpMemberDTOReq.toMember(passwordEncoder);

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
    public TokenDto login(SignUp_MemberDTO_REQ signUpMemberDTOReq){

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = signUpMemberDTOReq.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByMemberId 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 5. 토큰 발급
        return tokenDto;
    }
}