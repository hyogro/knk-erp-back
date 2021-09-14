package knk.erp.api.shlee.domain.schedule.dto.Attendance;

import knk.erp.api.shlee.domain.schedule.entity.RectifyAttendance;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class RectifyAttendanceListData {

    private Long id;
    private String memberName;
    private LocalDate attendanceDate;
    private LocalDateTime createDate;

    public RectifyAttendanceListData(RectifyAttendance rectifyAttendance){
        this.id = rectifyAttendance.getId();
        this.memberName = rectifyAttendance.getAuthor().getMemberName();
        this.attendanceDate = rectifyAttendance.getAttendanceDate();
        this.createDate = rectifyAttendance.getCreateDate();
    }


}
