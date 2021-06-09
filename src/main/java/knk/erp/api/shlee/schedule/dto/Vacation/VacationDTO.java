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
public class VacationDTO {
    private Long id;
    private String title;
    private String memo;
    private boolean approval1;
    private boolean approval2;
    private String approver1;
    private String approver2;
    private boolean reject;
    private String rejectMemo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String memberId;
    private Long departmentId;

    public Vacation toEntity(){
        return Vacation.builder().title(title).memo(memo).approval1(approval1).approval2(approval2)
                .rejectMemo(rejectMemo).startDate(startDate).endDate(endDate).memberId(memberId)
                .departmentId(departmentId).build();
    }

    public VacationDTO(Vacation vacation){
        this.id = vacation.getId();
        this.title = vacation.getTitle();
        this.memo = vacation.getMemo();
        this.approval1 = vacation.isApproval1();
        this.approval2 = vacation.isApproval2();
        this.approver1 = vacation.getApprover1();
        this.approver2 = vacation.getApprover2();
        this.reject = vacation.isReject();
        this.rejectMemo = vacation.getRejectMemo();
        this.startDate = vacation.getStartDate();
        this.endDate = vacation.getEndDate();
        this.memberId = vacation.getMemberId();
        this.departmentId = vacation.getDepartmentId();
    }
}
