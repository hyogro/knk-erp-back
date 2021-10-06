package knk.erp.api.shlee.common.util;

import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@NoArgsConstructor
public class EntityUtil {

    private static EntityUtil entityUtil = null;

    public static EntityUtil getInstance(){
        if (entityUtil == null) {
            entityUtil = new EntityUtil();
        }

        return entityUtil;
    }

    public Member getMember(MemberRepository memberRepository){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
    }

    public String getMemberId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Long getDepartmentId(MemberRepository memberRepository){
        return this.getMember(memberRepository).getDepartment().getId();
    }

}
