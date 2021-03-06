package knk.erp.api.shlee.domain.account.service;

import knk.erp.api.shlee.domain.account.dto.account.*;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.domain.account.entity.*;
import knk.erp.api.shlee.domain.account.util.AccountUtil;
import knk.erp.api.shlee.domain.account.util.SecurityUtil;
import knk.erp.api.shlee.common.dto.TokenDto;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import knk.erp.api.shlee.exception.exceptions.Account.AccountNotFoundMemberException;
import knk.erp.api.shlee.exception.exceptions.Account.AccountOverlapIdException;
import knk.erp.api.shlee.exception.exceptions.Account.AccountTargetIsLeaderException;
import knk.erp.api.shlee.exception.exceptions.Account.AccountWrongPasswordException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentNotFoundException;
import knk.erp.api.shlee.exception.exceptions.common.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    /* ?????? ?????? */
    @Transactional
    public void signup(MemberDTO_REQ memberDTOReq){
        throwIfOverlapId(memberDTOReq);

        Member member = memberDTOReq.toMember(passwordEncoder);
        Department department;

        if(memberDTOReq.getDepartmentId() != null) department = departmentRepository.getOne(memberDTOReq.getDepartmentId());
        else department = departmentRepository.findByDepartmentNameAndDeletedFalse("???????????????");

        member.setDepartment(department);
        memberRepository.save(member);
        department.getMemberList().add(member);
        departmentRepository.save(department);
    }

    // ???????????? api ?????? ??? ?????? ???????????? ID ????????????
    public void throwIfOverlapId(MemberDTO_REQ memberDTOReq){
        if(memberRepository.existsByMemberId(memberDTOReq.getMemberId())){
            throw new AccountOverlapIdException();
        }
    }

    /* Id ???????????? */
    @Transactional
    public boolean checkId(Check_existMemberIdDTO checkExistMemberIdDTO){
        return memberRepository.existsByMemberId(checkExistMemberIdDTO.getMemberId());
    }

    /* ????????? ??? Token ?????? */
    @Transactional
    public Login_TokenDTO_RES login(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken = loginDTO.toAuthentication();
        Authentication authentication;
        System.out.println(1);
        try{
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch(AuthenticationException e){
            System.out.println(2);
            throw new AccountWrongPasswordException();
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        Member me = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());

        return new Login_TokenDTO_RES(tokenDto, me.getMemberName());
    }

    /* ?????? ?????? ???????????? */
    @Transactional
    public List<Read_AccountDTO> readMember(){
        List<Member> memberList = memberRepository.findAllByDeletedIsFalse();
        return memberList.stream().map(Read_AccountDTO::new).collect(Collectors.toList());
    }

    /* ?????? ?????? ?????? ?????? */
    @Transactional
    public ReadDetail_AccountDTO readMemberDetail(String memberId){
        throwIfNotFoundMember(memberId);

        Member target = memberRepository.findByMemberIdAndDeletedIsFalse(memberId);
        return new ReadDetail_AccountDTO(target);
    }

    // ?????????????????? ?????????????????? member ?????? ??????
    public void throwIfNotFoundMember(String memberId){
        if(!memberRepository.existsByMemberIdAndDeletedFalse(memberId)){
            throw new AccountNotFoundMemberException();
        }
    }

    /* ?????? ?????? ?????? */
    @Transactional
    public void updateMember(String memberId, Update_AccountDTO_REQ updateAccountDTOReq){
        throwIfNotFoundMember(memberId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String level = authentication.getAuthorities().toString();
        Member target= memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);

        throwIfLowAuthority(updateAccountDTOReq, level, target);

        if (!Objects.equals(updateAccountDTOReq.getDep_id(), target.getDepartment().getId())) {
            throwIfNotFoundDepartment(updateAccountDTOReq.getDep_id());
            throwIfTargetIsLeader(target, updateAccountDTOReq.getDep_id());
        }

        Department department = departmentRepository.findByIdAndDeletedFalse(updateAccountDTOReq.getDep_id());

        accountUtil.updateSetMember(target, department, updateAccountDTOReq, passwordEncoder);
        memberRepository.save(target);
    }

    // ?????? ??????(???????????? ?????? ????????? ??????, ????????? ??????) ?????? ??????
    public void throwIfLowAuthority(Update_AccountDTO_REQ updateAccountDTOReq, String level, Member target){
        if(!securityUtil.checkAuthority(updateAccountDTOReq, level, target)) {
            throw new PermissionDeniedException();
        }
    }

    // ?????? ?????? ??? ?????? ????????? ?????? ?????? ?????? ??????
    public void throwIfNotFoundDepartment(Long depId){
        if(!departmentRepository.existsByIdAndDeletedFalse(depId)){
            throw new DepartmentNotFoundException();
        }
    }

    // ?????? ??? ?????? ????????? ????????? ????????? ?????? ?????? ??????
    public void throwIfTargetIsLeader(Member target, Long depId){
        if(departmentRepository.findByIdAndDeletedFalse(depId).getLeader() == target){
            throw new AccountTargetIsLeaderException();
        }
    }

    /* ?????? ?????? ?????? */
    @Transactional
    public void deleteMember(String memberId){
        throwIfNotFoundMember(memberId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String level = authentication.getAuthorities().toString();
        Member target = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);

        throwIfLowLevel(level, target);

        if(target.getDepartment() != null){
            throwIfTargetIsLeader(target, target.getDepartment().getId());
        }

        target.setDepartment(null);
        target.setDeleted(true);
        memberRepository.save(target);
    }

    // ?????? ??????(????????? ??????) ?????? ??????
    public void throwIfLowLevel(String myLevel, Member target){
        if(!securityUtil.checkTargetAuthority(myLevel, target)) {
            throw new PermissionDeniedException();
        }
    }
}
