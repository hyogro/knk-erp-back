package knk.erp.api.shlee.account.dto.member;

import knk.erp.api.shlee.account.entity.Authority;
import knk.erp.api.shlee.account.entity.Department;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_AccountDTO_REQ {
    private Long id;
    private String password;
    private Authority authority;
    private String phone;
    private float vacation;
    private Long dep_id;
}
