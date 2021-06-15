package knk.erp.api.shlee.schedule.dto.Schedule;

import knk.erp.api.shlee.schedule.entity.Schedule;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ScheduleDTO {
    private Long id;
    private String title;
    private String memo;
    private String viewOption;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Schedule toEntity(){
        return Schedule.builder().title(title).memo(memo).viewOption(viewOption).startDate(startDate).endDate(endDate).build();
    }

    public ScheduleDTO(Schedule schedule){
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.memo = schedule.getMemo();
        this.viewOption = schedule.getViewOption();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
    }
}
