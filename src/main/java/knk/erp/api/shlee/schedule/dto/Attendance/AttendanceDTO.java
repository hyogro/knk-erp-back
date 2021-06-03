package knk.erp.api.shlee.schedule.dto.Attendance;

import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AttendanceDTO extends Time {

    private Long id;
    private Long memberId;
    private LocalDate attendanceDate;
    private LocalDateTime onWork;
    private LocalDateTime offWork;

    public Attendance toEntity(){
        return Attendance.builder().memberId(memberId).attendanceDate(attendanceDate).onWork(onWork).offWork(offWork).build();
    }
}
