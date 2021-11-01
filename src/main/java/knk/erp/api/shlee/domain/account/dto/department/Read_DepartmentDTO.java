package knk.erp.api.shlee.domain.account.dto.department;

import knk.erp.api.shlee.domain.account.entity.Department;
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

    public Read_DepartmentDTO(Department department){
        this.dep_id = department.getId();
        this.departmentName = department.getDepartmentName();

        if(department.getLeader() == null) {this.leaderName = "리더없음";}
        else {this.leaderName = department.getLeader().getMemberName();}

        this.headCount = department.getMemberList().size();
    }
}
