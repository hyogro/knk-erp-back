package knk.erp.api.shlee.common.util;

import knk.erp.api.shlee.account.entity.Authority;
import knk.erp.api.shlee.account.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    //레벨 3 이상인지 여부 체크
    public int checkLevel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String lvl = authentication.getAuthorities().toString().replace("[ROLE_", "").replace("]", "");
        return lvl.equals("LVL2") || lvl.equals("MATERIALS") ? 2 :lvl.equals("LVL3") ? 3 : lvl.equals("LVL4") ? 4 : lvl.equals("ADMIN") ? 5 : 1;
    }

    // Authority -> Integer 변환
    public int authorityToInteger(Member member){
        Authority authority = member.getAuthority();
        return authority.equals(Authority.ROLE_LVL2) || authority.equals(Authority.ROLE_MATERIALS) ? 2 : authority.equals(Authority.ROLE_LVL3) ? 3 :
                authority.equals(Authority.ROLE_LVL4) ? 4 : authority.equals(Authority.ROLE_ADMIN) ? 5 : 1;
    }
}
