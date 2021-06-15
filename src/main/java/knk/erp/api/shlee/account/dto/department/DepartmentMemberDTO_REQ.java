package knk.erp.api.shlee.account.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DepartmentMemberDTO_REQ {
    private String memberId;
    private boolean dummy;
}
