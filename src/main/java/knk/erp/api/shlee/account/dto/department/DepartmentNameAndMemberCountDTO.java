package knk.erp.api.shlee.account.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DepartmentNameAndMemberCountDTO {
    private String departmentName;
    private int memberCount;
}
