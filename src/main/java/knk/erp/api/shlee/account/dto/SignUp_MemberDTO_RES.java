package knk.erp.api.shlee.account.dto;

import knk.erp.api.shlee.account.entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignUp_MemberDTO_RES {
    private String memberId;

    public static SignUp_MemberDTO_RES of(Member member) {
        return new SignUp_MemberDTO_RES(member.getMemberId());
    }
}
