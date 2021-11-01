package knk.erp.api.shlee.domain.account.dto.department;

import knk.erp.api.shlee.domain.account.entity.Department;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO_REQ {
    private String departmentName;
    private boolean dummy;

    public Department toDepartment(){
        return Department.builder()
                .departmentName(departmentName)
                .build();
    }
}
