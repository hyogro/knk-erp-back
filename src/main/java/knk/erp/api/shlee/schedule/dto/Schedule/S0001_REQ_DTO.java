package knk.erp.api.shlee.schedule.dto.Schedule;

import knk.erp.api.shlee.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class S0001_REQ_DTO {
    private Long id;
    private String title;
    private String memo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long memberId;
    private Long groupId;

    public Schedule toEntity(){
        return Schedule.builder().title(title).memo(memo).startDate(startDate).endDate(endDate).memberId(memberId)
                .groupId(groupId).build();
    }
}
