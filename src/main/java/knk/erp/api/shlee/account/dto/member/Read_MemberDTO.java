package knk.erp.api.shlee.account.dto.member;

import knk.erp.api.shlee.account.entity.Authority;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_MemberDTO {
    private String memberId;
    private String password;
    private String phone;
    private String memberName;
    private float vacation;
    private String departmentName;
    private Authority authority;
}
