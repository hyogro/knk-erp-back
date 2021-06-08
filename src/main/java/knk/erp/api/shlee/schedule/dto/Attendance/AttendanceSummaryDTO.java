package knk.erp.api.shlee.schedule.dto.Attendance;

import knk.erp.api.shlee.schedule.entity.Time;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class AttendanceSummaryDTO extends Time {

    public int onWork;
    public int yetWork;
    public int lateWork;
}
