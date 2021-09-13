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
public class AttendanceListData {

    private Long id;
    private LocalDate attendanceDate;
    private LocalTime onWork;
    private LocalTime offWork;
    private String uuid;

    public AttendanceListData(Attendance attendance){
        this.id = attendance.getId();
        this.attendanceDate = attendance.getAttendanceDate();
        this.onWork = attendance.getOnWork();
        this.offWork = attendance.getOffWork();
        this.uuid = attendance.getUuid();
    }
    public AttendanceListData(LocalDate attendanceDate, LocalTime onWork){
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
    }

    public Attendance toEntity(){
        return Attendance.builder().attendanceDate(attendanceDate).onWork(onWork).offWork(offWork).build();
    }
}
