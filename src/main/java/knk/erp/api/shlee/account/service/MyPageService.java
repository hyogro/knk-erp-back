package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.member.Read_MemberDTO;
import knk.erp.api.shlee.account.dto.my.GetMyInfo_MyPageDTO_RES;
import knk.erp.api.shlee.account.entity.DepartmentRepository;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional(readOnly = true)
    public GetMyInfo_MyPageDTO_RES getmyinfo(String memberId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            if(authentication != null && authentication.getName() != null) {
                memberId = authentication.getName();
                Member my = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                return new GetMyInfo_MyPageDTO_RES("GMI001", new Read_MemberDTO(my.getId(), my.getMemberId(),
                        null, my.getPhone(), my.getMemberName(), my.getVacation(), my.getDepartment().getId(),
                        my.getDepartment().getDepartmentName(), my.getAuthority()));
            }
            else return new GetMyInfo_MyPageDTO_RES("GMI003", "로그인을 해주세요");
        }catch(Exception e){
            return new GetMyInfo_MyPageDTO_RES("GMI002", e.getMessage());
        }
    }
}
