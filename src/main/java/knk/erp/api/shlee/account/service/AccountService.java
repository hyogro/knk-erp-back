package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.account.*;
import knk.erp.api.shlee.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.account.entity.*;
import knk.erp.api.shlee.account.util.AccountUtil;
import knk.erp.api.shlee.account.util.SecurityUtil;
import knk.erp.api.shlee.common.dto.TokenDto;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final SecurityUtil securityUtil;

    // 회원 가입
    @Transactional
    public SignUp_MemberDTO_RES signup(MemberDTO_REQ memberDTOReq){
        try {
            if(memberRepository.existsByMemberId(memberDTOReq.getMemberId())) {
                return new SignUp_MemberDTO_RES("SU003", "이미 가입된 ID");
            }

            Member member = memberDTOReq.toMember(passwordEncoder);
            Department department;

            if(memberDTOReq.getDepartmentId() != null) department = departmentRepository.getOne(memberDTOReq.getDepartmentId());
            else department = departmentRepository.findByDepartmentNameAndDeletedFalse("부서미지정");

            member.setDepartment(department);
            department.getMemberList().add(member);
            departmentRepository.save(department);
            memberRepository.save(member);

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

    // 회원 목록 읽어오기
    @Transactional
    public Read_AccountDTO_RES readMember(){
        try{
            List<Member> memberList = memberRepository.findAllByDeletedIsFalse();
            List<String> memberName = new ArrayList<>();
            List<String> memberId = new ArrayList<>();

            for(Member m : memberList){
                memberName.add(m.getMemberName());
                memberId.add(m.getMemberId());
            }

            return new Read_AccountDTO_RES("RA001", new Read_AccountDTO(memberId, memberName));
        }catch(Exception e){
            return new Read_AccountDTO_RES("RA002", e.getMessage());
        }
    }

    // 회원 정보 상세 보기
    @Transactional
    public ReadDetail_AccountDTO_RES readMemberDetail(String memberId){
        try{
            Member target = memberRepository.findByMemberIdAndDeletedIsFalse(memberId);
            return new ReadDetail_AccountDTO_RES("RDA001", new ReadDetail_AccountDTO(target.getMemberId(), target.getMemberName(),null,
                    target.getAuthority().toString(), target.getPhone(), target.getVacation(), target.getDepartment().getDepartmentName(),
                    target.getJoiningDate()));
        }catch(Exception e){
            return new ReadDetail_AccountDTO_RES("RDA002", e.getMessage());
        }
    }

    // 회원 정보 수정
    @Transactional
    public Update_AccountDTO_RES updateMember(String memberId, Update_AccountDTO_REQ updateAccountDTOReq){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String level = authentication.getAuthorities().toString();
            Member target= memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);

            if(securityUtil.checkAuthority(updateAccountDTOReq, level, target)){
                Department department = null;

                if(departmentRepository.existsByDepartmentNameAndDeletedFalse(updateAccountDTOReq.getDepartmentName())){
                    if(departmentRepository.getOne(target.getDepartment().getId()).getLeader() == target){
                        return new Update_AccountDTO_RES("UA004", "수정할 대상이 부서의 리더입니다.");
                    }
                    department = departmentRepository.findByDepartmentNameAndDeletedFalse(updateAccountDTOReq.getDepartmentName());
                }

                accountUtil.updateSetMember(target, department, updateAccountDTOReq, passwordEncoder);
                memberRepository.save(target);

                return new Update_AccountDTO_RES("UA001");
            }

            else return new Update_AccountDTO_RES("UA003", "해당 정보를 수정할 권한이 없습니다.");
        }catch(Exception e){
            return new Update_AccountDTO_RES("UA002", e.getMessage());
        }
    }

    // 회원 정보 삭제
    @Transactional
    public Delete_AccountDTO_RES deleteMember(String memberId){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String level = authentication.getAuthorities().toString();
            Member target = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);

            if(securityUtil.checkTargetAuthority(level, target)){
                if(target.getDepartment().getLeader() != null){
                    if(target.getDepartment().getLeader() == target){
                        Department department = departmentRepository.getOne(target.getDepartment().getId());
                        department.setLeader(null);
                        departmentRepository.save(department);
                    }
                }

                target.setDeleted(true);
                memberRepository.save(target);

                return new Delete_AccountDTO_RES("DA001");
            }

            else return new Delete_AccountDTO_RES("DA003", "해당 회원을 삭제할 권한이 없습니다.");
        }catch(Exception e){
            return new Delete_AccountDTO_RES("DA002", e.getMessage());
        }
    }
}
