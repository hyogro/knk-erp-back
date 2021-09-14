package knk.erp.api.shlee.domain.account.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Read_DepartmentDTO {
    private Long dep_id;
    private String departmentName;
    private String leaderName;
    private int headCount;
}
