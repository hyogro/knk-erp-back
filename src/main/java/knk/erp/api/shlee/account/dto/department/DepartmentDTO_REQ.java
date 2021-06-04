package knk.erp.api.shlee.account.dto.department;

import knk.erp.api.shlee.account.entity.Department;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DepartmentDTO_REQ {
    private Long dep_id;
    private String departmentName;

    public Department toDepartment(){
        return Department.builder()
                .departmentName(departmentName)
                .build();
    }
}
