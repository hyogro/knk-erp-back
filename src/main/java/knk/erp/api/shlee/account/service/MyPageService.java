package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.member.Read_MemberDTO;
import knk.erp.api.shlee.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.account.dto.my.GetMyInfo_MyPageDTO_RES;
import knk.erp.api.shlee.account.dto.my.UpdateSelf_MyPageDTO_RES;
import knk.erp.api.shlee.account.entity.DepartmentRepository;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.account.util.AccountUtil;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountUtil accountUtil;

    // 회원 본인 정보 불러오기
    @Transactional(readOnly = true)
    public GetMyInfo_MyPageDTO_RES getMyInfo(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member my = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            return new GetMyInfo_MyPageDTO_RES("GMI001", new Read_MemberDTO(my.getMemberId(), null, my.getPhone(),
                    my.getMemberName(), my.getVacation(), my.getDepartment().getDepartmentName(), my.getAuthority()));
        }catch(Exception e){
            return new GetMyInfo_MyPageDTO_RES("GMI002", e.getMessage());
        }
    }

    // 회원 본인 정보 수정
    @Transactional
    public UpdateSelf_MyPageDTO_RES updateSelf(Update_AccountDTO_REQ updateAccountDTOReq){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member my = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            accountUtil.updateSelfMember(my, updateAccountDTOReq, passwordEncoder);
            memberRepository.save(my);
            return new UpdateSelf_MyPageDTO_RES("USM001");
        }catch (Exception e){
            return new UpdateSelf_MyPageDTO_RES("USM002", e.getMessage());
        }
    }
}