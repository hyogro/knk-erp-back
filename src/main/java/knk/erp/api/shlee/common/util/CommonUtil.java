package knk.erp.api.shlee.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    public int checkMaster(Authentication authentication) {
        String lvl = authentication.getAuthorities().toString().replace("[ROLE_", "").replace("]", "");
        return lvl.equals("LVL2") ? 2 :lvl.equals("LVL3") ? 3 : lvl.equals("LVL4") ? 4 : lvl.equals("ADMIN") ? 5 : 1;
    }

}
