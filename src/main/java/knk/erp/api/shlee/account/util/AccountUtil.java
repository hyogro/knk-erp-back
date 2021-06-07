package knk.erp.api.shlee.account.util;

import knk.erp.api.shlee.account.dto.member.Read_MemberDTO_REQ;
import knk.erp.api.shlee.account.entity.Member;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountUtil {
    public List<Read_MemberDTO_REQ> getMemberList(List<Member> memberList){
        List<Read_MemberDTO_REQ> member_List = new ArrayList<>();
        for(Member member : memberList){
            member_List.add(new Read_MemberDTO_REQ(member.getMemberId(), null, member.getPhone(),
                    member.getMemberName(), member.getVacation(), member.getDepartment().getDepartmentName(), member.getAuthority()));
        }
        return member_List;
    }
}
