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
public class ScheduleListData {
    private Long id;
    private String title;
    private String viewOption;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ScheduleListData(Schedule schedule){
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.viewOption = schedule.getViewOption();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
    }
}
