package knk.erp.api.shlee.account.util;

import knk.erp.api.shlee.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.account.entity.Authority;
import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountUtil {

    public List<MemberDTO_REQ> getMemberList(List<Member> memberList){
        List<MemberDTO_REQ> member_List = new ArrayList<>();
        for(Member member : memberList){
            member_List.add(new MemberDTO_REQ(member.getId(), member.getMemberId(), null, member.getPhone(),
                    member.getMemberName(), member.getVacation(), member.getDepartment().getId(),
                    member.getDepartment().getDepartmentName(), member.getAuthority()));
        }
        return member_List;
    }

    public void updateSetMember(Member member, Department department, Update_AccountDTO_REQ updateAccountDTOReq,
                                BCryptPasswordEncoder passwordEncoder){
        member.setPassword(passwordEncoder.encode(updateAccountDTOReq.getPassword()));
        member.setAuthority(toAuthority(updateAccountDTOReq));
        member.setPhone(updateAccountDTOReq.getPhone());
        member.setVacation(updateAccountDTOReq.getVacation());
        member.setDepartment(department);
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
