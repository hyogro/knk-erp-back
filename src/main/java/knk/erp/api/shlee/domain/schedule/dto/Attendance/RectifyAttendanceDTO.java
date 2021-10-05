package knk.erp.api.shlee.domain.schedule.dto.Attendance;

import knk.erp.api.shlee.domain.schedule.entity.RectifyAttendance;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class RectifyAttendanceDTO {

    private Long id;
    private LocalDate attendanceDate;
    private LocalTime onWork;
    private LocalTime offWork;
    private boolean approval1;
    private boolean approval2;
    private String memo;

    public RectifyAttendance toEntity(){
        return RectifyAttendance.builder().attendanceDate(attendanceDate).onWork(onWork).offWork(offWork).memo(memo).build();
    }

    public RectifyAttendanceDTO(RectifyAttendance rectifyAttendance){
        this.id = rectifyAttendance.getId();
        this.attendanceDate = rectifyAttendance.getAttendanceDate();
        this.onWork = rectifyAttendance.getOnWork();
        this.offWork = rectifyAttendance.getOffWork();
        this.approval1 = rectifyAttendance.isApproval1();
        this.approval2 = rectifyAttendance.isApproval2();
        this.memo = rectifyAttendance.getMemo();
    }


}
