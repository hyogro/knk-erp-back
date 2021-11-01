package knk.erp.api.shlee.domain.account.dto.department;

import knk.erp.api.shlee.domain.account.entity.Department;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_DepartmentDTO {
    private String departmentName;
    private String leaderId;
    private String leaderName;
    private int headCount;

    public ReadDetail_DepartmentDTO(Department department){
        this.departmentName = department.getDepartmentName();
        if(department.getLeader() == null){
            this.leaderId = "파트장이 지정되지 않음";
            this.leaderName = "파트장이 지정되지 않음";
        }
        else {
            this.leaderId = department.getLeader().getMemberId();
            this.leaderName = department.getLeader().getMemberName();
        }
    }
}
