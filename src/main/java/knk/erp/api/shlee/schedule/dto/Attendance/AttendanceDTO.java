package knk.erp.api.shlee.schedule.dto.Attendance;

import knk.erp.api.shlee.schedule.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AttendanceDTO{

    private Long id;
    private String memberId;
    private Long departmentId;
    private LocalDate attendanceDate;
    private LocalTime onWork;
    private LocalTime offWork;

    public AttendanceDTO(Attendance attendance){
        this.id = attendance.getId();
        this.memberId = attendance.getMemberId();
        this.departmentId = attendance.getDepartmentId();
        this.attendanceDate = attendance.getAttendanceDate();
        this.onWork = attendance.getOnWork();
        this.offWork = attendance.getOffWork();
    }
    public AttendanceDTO(String memberId, LocalDate attendanceDate, LocalTime onWork, Long departmentId){
        this.memberId = memberId;
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
        this.departmentId = departmentId;
    }

    public Attendance toEntity(){
        return Attendance.builder().memberId(memberId).departmentId(departmentId).attendanceDate(attendanceDate).onWork(onWork).offWork(offWork).build();
    }
}
