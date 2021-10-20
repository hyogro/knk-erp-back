package knk.erp.api.shlee.domain.account.service;

import knk.erp.api.shlee.domain.account.dto.account.*;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.domain.account.entity.*;
import knk.erp.api.shlee.domain.account.util.AccountUtil;
import knk.erp.api.shlee.domain.account.util.SecurityUtil;
import knk.erp.api.shlee.common.dto.TokenDto;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import knk.erp.api.shlee.exception.exceptions.Account.AccountOverlabIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /* 회원 가입 */
    @Transactional
    public void signup(MemberDTO_REQ memberDTOReq){
        ThrowIfOverlabId(memberDTOReq);

        Member member = memberDTOReq.toMember(passwordEncoder);
        Department department;

        if(memberDTOReq.getDepartmentId() != null) department = departmentRepository.getOne(memberDTOReq.getDepartmentId());
        else department = departmentRepository.findByDepartmentNameAndDeletedFalse("부서미지정");

        member.setDepartment(department);
        memberRepository.save(member);
        department.getMemberList().add(member);
        departmentRepository.save(department);
    }

    public void ThrowIfOverlabId(MemberDTO_REQ memberDTOReq){
        if(memberRepository.existsByMemberId(memberDTOReq.getMemberId())){
            throw new AccountOverlabIdException();
        }
    }

    /* Id 중복체크 */
    @Transactional
    public boolean checkId(Check_existMemberIdDTO checkExistMemberIdDTO){
        return memberRepository.existsByMemberId(checkExistMemberIdDTO.getMemberId());
    }

    // 로그인 및 Token 발급
    @Transactional
    public Login_TokenDTO_RES login(MemberDTO_REQ MemberDTOReq){

        UsernamePasswordAuthenticationToken authenticationToken = MemberDTOReq.toAuthentication();

        try{
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            Member me = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());

            return new Login_TokenDTO_RES("LI001", tokenDto, me.getMemberName());
        }catch(Exception e){
            return new Login_TokenDTO_RES("LI002", e.getMessage());
        }
    }

    // 회원 목록 읽어오기
    @Transactional
    public List<Read_AccountDTO> readMember(){
        List<Member> memberList = memberRepository.findAllByDeletedIsFalse();
        return memberList.stream().map(Read_AccountDTO::new).collect(Collectors.toList());
    }

    // 회원 정보 상세 보기
    @Transactional
    public ReadDetail_AccountDTO readMemberDetail(String memberId){
        Member target = memberRepository.findByMemberIdAndDeletedIsFalse(memberId);
        return new ReadDetail_AccountDTO(target);
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

                if(departmentRepository.existsByIdAndDeletedFalse(updateAccountDTOReq.getDep_id())){
                    if(departmentRepository.getOne(target.getDepartment().getId()).getLeader() == target
                            && !updateAccountDTOReq.getDep_id().equals(target.getDepartment().getId())){
                        return new Update_AccountDTO_RES("UA004", "수정할 대상이 부서의 리더입니다.");
                    }
                    department = departmentRepository.findByIdAndDeletedFalse(updateAccountDTOReq.getDep_id());
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

                target.setDepartment(null);
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
