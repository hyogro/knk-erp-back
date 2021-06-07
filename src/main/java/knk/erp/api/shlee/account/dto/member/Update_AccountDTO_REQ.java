package knk.erp.api.shlee.account.dto.member;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_AccountDTO_REQ {
    private Long id;
    private String password;
    private String authority;
    private String phone;
    private float vacation;
    private Long dep_id;

}
