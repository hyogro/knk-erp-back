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
    private boolean lvl2Approve;
    private boolean lvl3Approve;
    private String approveMemo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long memberId;
    private Long groupId;

    public Vacation toEntity(){
        return Vacation.builder().title(title).memo(memo).lvl2Approve(lvl2Approve).lvl3Approve(lvl3Approve)
                .approveMemo(approveMemo).startDate(startDate).endDate(endDate).memberId(memberId)
                .groupId(groupId).build();
    }
}
