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
    private boolean approval_1;
    private boolean approval_2;
    private String approveMemo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String memberId;
    private Long departmentId;

    public Vacation toEntity(){
        return Vacation.builder().title(title).memo(memo).approval_1(approval_1).approval_2(approval_2)
                .approveMemo(approveMemo).startDate(startDate).endDate(endDate).memberId(memberId)
                .departmentId(departmentId).build();
    }
}
