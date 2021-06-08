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
    private boolean approval_1;
    private boolean approval_2;
    private String approver_1;
    private String approver_2;
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
        this.approval_1 = rectifyAttendance.isApproval_1();
        this.approval_2 = rectifyAttendance.isApproval_2();
        this.approver_1 = rectifyAttendance.getApprover_1();
        this.approver_2 = rectifyAttendance.getApprover_2();
        this.memo = rectifyAttendance.getMemo();
    }


}
