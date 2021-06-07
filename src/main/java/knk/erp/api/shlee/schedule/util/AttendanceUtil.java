package knk.erp.api.shlee.schedule.util;

import knk.erp.api.shlee.schedule.dto.Attendance.AttendanceDTO;
import knk.erp.api.shlee.schedule.dto.Attendance.RectifyAttendanceDTO;
import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttendanceUtil {
    public List<AttendanceDTO> AttendanceListToDTO(List<Attendance> attendanceList){
        List<AttendanceDTO> attendanceDTOList = new ArrayList<>();
        for(Attendance attendance : attendanceList){
            attendanceDTOList.add(new AttendanceDTO(attendance));
        }
        return attendanceDTOList;
    }

    public List<RectifyAttendanceDTO> RectifyAttendanceListToDTO(List<RectifyAttendance> rectifyAttendanceList){
        List<RectifyAttendanceDTO> rectifyAttendanceDTOList = new ArrayList<>();
        for(RectifyAttendance rectifyAttendance : rectifyAttendanceList){
            rectifyAttendanceDTOList.add(new RectifyAttendanceDTO(rectifyAttendance));
        }
        return rectifyAttendanceDTOList;
    }

    public Attendance RectifyToAttendance(RectifyAttendance rectifyAttendance){
        return Attendance.builder().memberId(rectifyAttendance.getMemberId()).departmentId(rectifyAttendance.getDepartmentId()).attendanceDate(rectifyAttendance.getAttendanceDate()).
                onWork(rectifyAttendance.getOnWork()).offWork(rectifyAttendance.getOffWork()).build();
    }

    public RectifyAttendance AttendanceToRectify(Attendance attendance, RectifyAttendanceDTO rectifyAttendanceDTO){
        return RectifyAttendance.builder().memberId(attendance.getMemberId()).attendanceDate(attendance.getAttendanceDate()).
                onWork(rectifyAttendanceDTO.getOnWork()).offWork(rectifyAttendanceDTO.getOffWork()).memo(rectifyAttendanceDTO.getMemo()).targetId(attendance.getId()).build();
    }



}
