package knk.erp.api.shlee.domain.schedule.util;

import knk.erp.api.shlee.domain.schedule.dto.Attendance.*;
import knk.erp.api.shlee.domain.schedule.entity.Attendance;
import knk.erp.api.shlee.domain.schedule.entity.RectifyAttendance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttendanceUtil {

    public List<Object> AttendanceUuidListToDTO(List<Attendance> attendanceList){
        List<Object> attendanceUuidDTOList = new ArrayList<>();
        for(Attendance attendance : attendanceList){
            attendanceUuidDTOList.add(new AttendanceUuidData(attendance));
        }
        return attendanceUuidDTOList;
    }

    public List<Object> AttendanceListToDTO(List<Attendance> attendanceList){
        List<Object> attendanceDTOList = new ArrayList<>();
        for(Attendance attendance : attendanceList){
            attendanceDTOList.add(new AttendanceListData(attendance));
        }
        return attendanceDTOList;
    }

    public List<Object> RectifyAttendanceListToDTO(List<RectifyAttendance> rectifyAttendanceList){
        List<Object> rectifyAttendanceDTOList = new ArrayList<>();
        for(RectifyAttendance rectifyAttendance : rectifyAttendanceList){
            rectifyAttendanceDTOList.add(new RectifyAttendanceListData(rectifyAttendance));
        }
        return rectifyAttendanceDTOList;
    }

    public Attendance RectifyToAttendance(RectifyAttendance rectifyAttendance){
        return Attendance.builder().attendanceDate(rectifyAttendance.getAttendanceDate()).
                onWork(rectifyAttendance.getOnWork()).offWork(rectifyAttendance.getOffWork()).build();
    }

    public RectifyAttendance AttendanceToRectify(Attendance attendance, RectifyAttendanceDTO rectifyAttendanceDTO){
        return RectifyAttendance.builder().attendanceDate(attendance.getAttendanceDate()).
                onWork(rectifyAttendanceDTO.getOnWork()).offWork(rectifyAttendanceDTO.getOffWork()).memo(rectifyAttendanceDTO.getMemo()).targetId(attendance.getId()).build();
    }



}
