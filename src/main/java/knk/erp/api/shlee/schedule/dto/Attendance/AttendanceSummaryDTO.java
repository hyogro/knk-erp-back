package knk.erp.api.shlee.schedule.dto.Attendance;

import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Time;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class AttendanceSummaryDTO extends Time {

    public int onWork;
    public int yetWork;
    public int lateWork;
    public int vacation;
}
