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
    private String type;
    private String memo;
    private boolean approval1;
    private boolean approval2;
    private boolean reject;
    private String rejectMemo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime requestDate;

    public Vacation toEntity(){
        return Vacation.builder().type(type).memo(memo).startDate(startDate).endDate(endDate).build();
    }

    public VacationDTO(Vacation vacation){
        this.id = vacation.getId();
        this.type = vacation.getType();
        this.memo = vacation.getMemo();
        this.approval1 = vacation.isApproval1();
        this.approval2 = vacation.isApproval2();
        this.reject = vacation.isReject();
        this.rejectMemo = vacation.getRejectMemo();
        this.startDate = vacation.getStartDate();
        this.endDate = vacation.getEndDate();
        this.requestDate = vacation.getCreateDate();
    }
}
