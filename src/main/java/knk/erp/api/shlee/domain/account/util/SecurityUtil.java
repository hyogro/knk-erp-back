package knk.erp.api.shlee.domain.account.util;

import knk.erp.api.shlee.domain.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.domain.account.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    // 회원 정보의 권한을 수정할 때 선택한 권한이 수정하려는 사람의 권한보다 높은지 체크
    public boolean checkAuthority(Update_AccountDTO_REQ updateAccountDTOReq, String level, Member target){
        int my_level;
        int update_level;
        if(checkTargetAuthority(level, target)){
            if(level.equals("[ROLE_ADMIN]")) return true;
            else my_level = Integer.parseInt(level.replace("[ROLE_LVL","").replace("]",""));

            if(updateAccountDTOReq.getAuthority() != null){
                if(updateAccountDTOReq.getAuthority().equals("ROLE_ADMIN")) update_level = 5;
                else if(updateAccountDTOReq.getAuthority().equals("ROLE_MANAGE")) update_level = 1;
                else if(updateAccountDTOReq.getAuthority().equals("ROLE_MATERIALS")) update_level = 2;
                else update_level = Integer.parseInt(updateAccountDTOReq.getAuthority().replace("ROLE_LVL",""));
            }
            else update_level = 0;

            return my_level >= update_level;
        }

        return false;
    }

    // 회원 정보를 수정 또는 삭제할 대상이 행위자보다 권한이 높은지 체크
    public boolean checkTargetAuthority(String level, Member target){
        int my_level;
        int target_level;

        if(level.equals("[ROLE_ADMIN]")) return true;
        else if(level.equals("[ROLE_MANAGE]")) my_level = 1;
        else if(level.equals("[ROLE_MATERIALS")) my_level = 2;
        else my_level = Integer.parseInt(level.replace("[ROLE_LVL","").replace("]",""));

        if(target.getAuthority().toString().equals("ROLE_ADMIN")) target_level = 5;
        else if(target.getAuthority().toString().equals("ROLE_MANAGE")) target_level = 1;
        else if(target.getAuthority().toString().equals("ROLE_MATERIALS")) target_level = 2;
        else target_level = Integer.parseInt(target.getAuthority().toString().replace("ROLE_LVL",""));

        return my_level >= target_level;
    }

}
