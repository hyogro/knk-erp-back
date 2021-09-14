package knk.erp.api.shlee.domain.account.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Update_DepartmentLeaderDTO_REQ {
    private String memberId;
    private boolean dummy;
}
