package knk.erp.api.shlee.schedule.dto.Attendance;

import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class RectifyAttendanceDetailData {

    private Long id;
    private LocalDate attendanceDate;
    private LocalTime onWork;
    private LocalTime offWork;
    private boolean approval1;
    private boolean approval2;
    private String approver1;
    private String approver2;
    private String memo;

    public RectifyAttendanceDetailData(RectifyAttendance rectifyAttendance){
        this.id = rectifyAttendance.getId();
        this.attendanceDate = rectifyAttendance.getAttendanceDate();
        this.onWork = rectifyAttendance.getOnWork();
        this.offWork = rectifyAttendance.getOffWork();
        this.approval1 = rectifyAttendance.isApproval1();
        this.approval2 = rectifyAttendance.isApproval2();
        if(this.approval1)
            this.approver1 = rectifyAttendance.getApprover1().getMemberName();
        if(this.approval2)
            this.approver2 = rectifyAttendance.getApprover2().getMemberName();
        this.memo = rectifyAttendance.getMemo();
    }


}
