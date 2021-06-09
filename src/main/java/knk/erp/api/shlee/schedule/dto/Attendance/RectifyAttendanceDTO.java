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
public class RectifyAttendanceDTO {

    private Long id;
    private String memberId;
    private Long departmentId;
    private LocalDate attendanceDate;
    private LocalTime onWork;
    private LocalTime offWork;
    private boolean approval1;
    private boolean approval2;
    private String approver1;
    private String approver2;
    private String memo;

    public RectifyAttendance toEntity(){
        return RectifyAttendance.builder().memberId(memberId).departmentId(departmentId).attendanceDate(attendanceDate).onWork(onWork).offWork(offWork).memo(memo).build();
    }

    public RectifyAttendanceDTO(RectifyAttendance rectifyAttendance){
        this.id = rectifyAttendance.getId();
        this.memberId = rectifyAttendance.getMemberId();
        this.departmentId = rectifyAttendance.getDepartmentId();
        this.attendanceDate = rectifyAttendance.getAttendanceDate();
        this.onWork = rectifyAttendance.getOnWork();
        this.offWork = rectifyAttendance.getOffWork();
        this.approval1 = rectifyAttendance.isApproval1();
        this.approval2 = rectifyAttendance.isApproval2();
        this.approver1 = rectifyAttendance.getApprover1();
        this.approver2 = rectifyAttendance.getApprover2();
        this.memo = rectifyAttendance.getMemo();
    }


}
