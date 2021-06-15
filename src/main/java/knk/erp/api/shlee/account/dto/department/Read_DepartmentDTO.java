package knk.erp.api.shlee.account.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Read_DepartmentDTO {
    private List<Long> dep_id;
    private List<String> departmentName;
}
