package knk.erp.api.shlee.domain.schedule.dto.Vacation;

import knk.erp.api.shlee.domain.schedule.entity.Vacation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class VacationListData {
    private Long id;
    private String type;
    private boolean approval1;
    private boolean approval2;
    private boolean reject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime requestDate;
    private String memberName;
    private String departmentName;

    public VacationListData(Vacation vacation){
        this.id = vacation.getId();
        this.type = vacation.getType();
        this.approval1 = vacation.isApproval1();
        this.approval2 = vacation.isApproval2();
        this.reject = vacation.isReject();
        this.startDate = vacation.getStartDate();
        this.endDate = vacation.getEndDate();
        this.requestDate = vacation.getCreateDate();
        this.memberName = vacation.getAuthor().getMemberName();
        this.departmentName = vacation.getAuthor().getDepartment().getDepartmentName();
    }
}
