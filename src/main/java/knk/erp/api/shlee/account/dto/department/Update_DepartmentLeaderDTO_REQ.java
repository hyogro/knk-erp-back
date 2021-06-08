package knk.erp.api.shlee.account.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Update_DepartmentLeaderDTO_REQ {
    private Long dep_id;
    private String memberId;
}
