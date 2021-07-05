package knk.erp.api.shlee.schedule.dto.Schedule;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    //기념일(생일)용
    public ScheduleListData(int year, Member member){
        this.id = null;
        this.title = member.getMemberName() + "-생일";
        this.viewOption = member.isBirthDateSolar()+"";
        LocalDate test = LocalDate.of(year, member.getBirthDate().getMonthValue(), member.getBirthDate().getDayOfMonth());

        this.startDate = LocalDateTime.of(test, LocalTime.of(9,0));
        this.endDate = LocalDateTime.of(test, LocalTime.of(18,0));
    }
}
