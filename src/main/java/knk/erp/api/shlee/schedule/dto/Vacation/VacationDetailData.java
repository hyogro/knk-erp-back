package knk.erp.api.shlee.schedule.dto.Vacation;

import knk.erp.api.shlee.schedule.entity.Vacation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class VacationDetailData {
    private Long id;
    private String type;
    private String memo;
    private boolean approval1;
    private boolean approval2;
    private String approver1;
    private String approver2;
    private boolean reject;
    private String rejectMemo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String memberName;
    private String departmentName;
    private LocalDateTime requestDate;

    public VacationDetailData(Vacation vacation){
        this.id = vacation.getId();
        this.type = vacation.getType();
        this.memo = vacation.getMemo();
        this.approval1 = vacation.isApproval1();
        this.approval2 = vacation.isApproval2();
        this.approver1 = vacation.getApprover1().getMemberName();
        this.approver2 = vacation.getApprover2().getMemberName();
        this.reject = vacation.isReject();
        this.rejectMemo = vacation.getRejectMemo();
        this.startDate = vacation.getStartDate();
        this.endDate = vacation.getEndDate();
        this.memberName = vacation.getAuthor().getMemberName();
        this.departmentName = vacation.getAuthor().getDepartment().getDepartmentName();
        this.requestDate = vacation.getCreateDate();
    }
}
