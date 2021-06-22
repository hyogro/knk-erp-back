package knk.erp.api.shlee.account.util;

import knk.erp.api.shlee.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.account.entity.Authority;
import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountUtil {

    public void updateSetMember(Member member, Department department, Update_AccountDTO_REQ updateAccountDTOReq,
                                BCryptPasswordEncoder passwordEncoder){
        if(updateAccountDTOReq.getPassword() != null){
            member.setPassword(passwordEncoder.encode(updateAccountDTOReq.getPassword()));
        }

        if(updateAccountDTOReq.getAuthority() != null){
            member.setAuthority(toAuthority(updateAccountDTOReq));
        }

        if(updateAccountDTOReq.getPhone() != null){
            member.setPhone(updateAccountDTOReq.getPhone());
        }

        if(updateAccountDTOReq.getVacation() != null){
            member.setVacation(Integer.parseInt(updateAccountDTOReq.getVacation())*8*60);
        }

        if(department != null){
            member.setDepartment(department);
        }

        if(updateAccountDTOReq.getJoiningDate() != null){
            member.setJoiningDate(updateAccountDTOReq.getJoiningDate());
        }

        if(updateAccountDTOReq.getAddress() != null){
            member.setAddress(updateAccountDTOReq.getAddress());
        }

        if(updateAccountDTOReq.getEmail() != null){
            member.setEmail(updateAccountDTOReq.getEmail());
        }
    }

    public void updateSelfMember(Member my, Update_AccountDTO_REQ updateAccountDTOReq, BCryptPasswordEncoder passwordEncoder){
        if(updateAccountDTOReq.getPassword()!=null){
            my.setPassword(passwordEncoder.encode(updateAccountDTOReq.getPassword()));
        }
        if(updateAccountDTOReq.getPhone()!=null){
           my.setPhone(updateAccountDTOReq.getPhone());
        }

        if(updateAccountDTOReq.getAddress() != null){
            my.setAddress(updateAccountDTOReq.getAddress());
        }

        if(updateAccountDTOReq.getEmail() != null){
            my.setEmail(updateAccountDTOReq.getEmail());
        }
    }

    public Authority toAuthority(Update_AccountDTO_REQ updateAccountDTOReq){
        Authority authority;
        if(updateAccountDTOReq.getAuthority().equals("ROLE_ADMIN")){
            authority = Authority.ROLE_ADMIN;
        }
        else if(updateAccountDTOReq.getAuthority().equals("ROLE_LVL2")){
            authority = Authority.ROLE_LVL2;
        }
        else if(updateAccountDTOReq.getAuthority().equals("ROLE_LVL3")){
            authority = Authority.ROLE_LVL3;
        }
        else if(updateAccountDTOReq.getAuthority().equals("ROLE_LVL4")){
            authority = Authority.ROLE_LVL4;
        }
        else{
            authority = Authority.ROLE_LVL1;
        }

        return authority;
    }
}
