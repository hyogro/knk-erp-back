package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.*;
import knk.erp.api.shlee.account.entity.*;
import knk.erp.api.shlee.account.jwt.TokenProvider;
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
    private final RefreshTokenRepository refreshTokenRepository;

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

        if(memberRepository.existsByMemberId(signUpMemberDTOReq.getMemberId())){
            throw new RuntimeException("이미 가입되어있는 ID 입니다.");
        }
        Member member = signUpMemberDTOReq.toMember(passwordEncoder);

        member.setDepartment(department);

        department.getMemberList().add(member);
        departmentRepository.save(department);

        return SignUp_MemberDTO_RES.of(memberRepository.save(member));
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

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    // Access Token 재발급
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){

        // 1. Refresh Token 검증
        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if(!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 7. 토큰 발급
        return tokenDto;
    }
}
