package knk.erp.api.shlee.domain.account.service;

import knk.erp.api.shlee.domain.account.dto.member.Read_MemberDTO;
import knk.erp.api.shlee.domain.account.dto.my.Update_SelfDTO;
import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.domain.account.util.AccountUtil;
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

    /* 회원 본인 정보 불러오기 */
    @Transactional
    public Read_MemberDTO getMyInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member my = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
        return new Read_MemberDTO(my);
    }

    /* 회원 본인 정보 수정 */
    @Transactional
    public void updateSelf(Update_SelfDTO updateSelfDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member my = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
        accountUtil.updateSelfMember(my, updateSelfDTO, passwordEncoder);
        memberRepository.save(my);
    }

    /* 회원 본인 추가 연차일수 보기 */
    @Transactional
    public int getMyVacation(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member me = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());

        return me.getVacation();
    }
}