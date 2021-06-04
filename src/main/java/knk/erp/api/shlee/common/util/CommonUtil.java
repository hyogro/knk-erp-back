package knk.erp.api.shlee.common.util;

import knk.erp.api.shlee.account.entity.Authority;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
    public boolean isAuth(Authentication authentication, Authority targetAuthority){
        authentication.getAuthorities().contains(targetAuthority);
        String auth_1 = authentication.getAuthorities().toString().replace("[","").replace("]", "");
        return auth_1.equals(targetAuthority.toString());
    }

}
