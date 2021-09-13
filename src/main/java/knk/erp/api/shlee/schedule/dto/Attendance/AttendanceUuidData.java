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
public class AttendanceUuidData {

    private Long mid;
    private String memberName;
    private String memberId;

    private Long aid;
    private LocalDate attendanceDate;
    private LocalTime onWork;
    private LocalTime offWork;
    private String uuid;

    public AttendanceUuidData(Attendance attendance){

        this.mid = attendance.getAuthor().getId();
        this.memberName = attendance.getAuthor().getMemberName();
        this.memberId = attendance.getAuthor().getMemberId();

        this.aid = attendance.getId();
        this.attendanceDate = attendance.getAttendanceDate();
        this.onWork = attendance.getOnWork();
        this.offWork = attendance.getOffWork();
        this.uuid = attendance.getUuid();
    }
    public AttendanceUuidData(LocalDate attendanceDate, LocalTime onWork){
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
    }

    public Attendance toEntity(){
        return Attendance.builder().attendanceDate(attendanceDate).onWork(onWork).offWork(offWork).build();
    }
}
