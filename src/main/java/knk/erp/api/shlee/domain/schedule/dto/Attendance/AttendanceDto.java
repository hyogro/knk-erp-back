package knk.erp.api.shlee.domain.schedule.dto.Attendance;

import knk.erp.api.shlee.domain.schedule.entity.Attendance;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class AttendanceDto {

    private Long id;
    private LocalDate attendanceDate;
    private LocalTime onWork;
    private LocalTime offWork;
    private String uuid;

    public AttendanceDto(Attendance attendance){
        this.id = attendance.getId();
        this.attendanceDate = attendance.getAttendanceDate();
        this.onWork = attendance.getOnWork();
        this.offWork = attendance.getOffWork();
        this.uuid = attendance.getUuid();
    }

    public Attendance toEntity(){
        return Attendance
                .builder()
                .attendanceDate(attendanceDate)
                .onWork(onWork)
                .offWork(offWork)
                .uuid(uuid)
                .build();
    }
}
