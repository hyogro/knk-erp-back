package knk.erp.api.shlee.schedule.dto.Attendance;

import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Time;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class RectifyAttendanceDTO extends Time {

    private Long id;
    private String memberId;
    private LocalDate attendanceDate;
    private LocalDateTime onWork;
    private LocalDateTime offWork;
    private boolean approval_1;
    private boolean approval_2;
    private String memo;

    public RectifyAttendance toEntity(){
        return RectifyAttendance.builder().memberId(memberId).attendanceDate(attendanceDate).onWork(onWork).offWork(offWork).memo(memo).build();
    }


}
