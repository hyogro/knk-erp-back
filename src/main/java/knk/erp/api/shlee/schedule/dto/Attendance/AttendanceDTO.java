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
    private LocalDate attendanceDate;
    private LocalTime onWork;
    private LocalTime offWork;
    private String uuid;

    public AttendanceDTO(Attendance attendance){
        this.id = attendance.getId();
        this.attendanceDate = attendance.getAttendanceDate();
        this.onWork = attendance.getOnWork();
        this.offWork = attendance.getOffWork();
        this.uuid = attendance.getUuid();
    }
    public AttendanceDTO(LocalDate attendanceDate, LocalTime onWork, String uuid){
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
        this.uuid = uuid;
    }

    public Attendance toEntity(){
        return Attendance.builder().attendanceDate(attendanceDate).onWork(onWork).offWork(offWork).uuid(uuid).build();
    }
}
