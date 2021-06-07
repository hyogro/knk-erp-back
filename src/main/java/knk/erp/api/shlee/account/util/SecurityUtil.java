package knk.erp.api.shlee.account.util;

import knk.erp.api.shlee.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SecurityUtil {

    // 회원 정보의 권한을 수정할 때 선택한 권한이 수정하려는 사람의 권한보다 높은지 체크
    public boolean checkAuthority(Update_AccountDTO_REQ updateAccountDTOReq, String level){
        if(updateAccountDTOReq.getAuthority().toString().equals("ROLE_LVL3") && level.equals("LVL3")){
            return false;
        }
        else if(updateAccountDTOReq.getAuthority().toString().equals("ROLE_LVL4")){
            if(level.equals("ROLE_LVL3") || level.equals("ROLE_LVL4")){
                return false;
            }
        }
        else if(updateAccountDTOReq.getAuthority().toString().equals("ROLE_ADMIN") && !level.equals("ROLE_ADMIN")){
            return false;
        }
        return true;
    }

    // 회원 정보를 수정할 대상이 수정하려는 사람보다 권한이 높은지 체크
    public boolean checkUpdateTargetAuthority(Update_AccountDTO_REQ updateAccountDTOReq, String level, Member target){
            if(level.equals("LVL3")){
                if(target.getAuthority().toString().equals("ROLE_LVL4")){
                    return false;
                }
                else if(target.getAuthority().toString().equals("ROLE_ADMIN")){
                    return false;
                }
            }
            else if(level.equals("LVL4")){
                if(target.getAuthority().toString().equals("ROLE_ADMIN")){
                    return false;
                }
            }
            return true;
    }
}
