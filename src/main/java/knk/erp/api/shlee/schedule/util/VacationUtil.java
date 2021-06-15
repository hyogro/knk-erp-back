package knk.erp.api.shlee.schedule.util;

import knk.erp.api.shlee.schedule.dto.Attendance.AttendanceDTO;
import knk.erp.api.shlee.schedule.dto.Attendance.RectifyAttendanceDTO;
import knk.erp.api.shlee.schedule.dto.Vacation.VacationDTO;
import knk.erp.api.shlee.schedule.dto.Vacation.VacationListData;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VacationUtil {
    public List<VacationListData> VacationListToDTO(List<Vacation> vacationList){
        List<VacationListData> vacationDTOList = new ArrayList<>();
        for(Vacation vacation : vacationList){
            vacationDTOList.add(new VacationListData(vacation));
        }
        return vacationDTOList;
    }
//
//    public List<RectifyAttendanceDTO> RectifyAttendanceListToDTO(List<RectifyAttendance> rectifyAttendanceList){
//        List<RectifyAttendanceDTO> rectifyAttendanceDTOList = new ArrayList<>();
//        for(RectifyAttendance rectifyAttendance : rectifyAttendanceList){
//            rectifyAttendanceDTOList.add(new RectifyAttendanceDTO(rectifyAttendance));
//        }
//        return rectifyAttendanceDTOList;
//    }
//
//    public Attendance RectifyToAttendance(RectifyAttendance rectifyAttendance){
//        return Attendance.builder().memberId(rectifyAttendance.getMemberId()).departmentId(rectifyAttendance.getDepartmentId()).attendanceDate(rectifyAttendance.getAttendanceDate()).
//                onWork(rectifyAttendance.getOnWork()).offWork(rectifyAttendance.getOffWork()).build();
//    }
//
//    public RectifyAttendance AttendanceToRectify(Attendance attendance, RectifyAttendanceDTO rectifyAttendanceDTO){
//        return RectifyAttendance.builder().memberId(attendance.getMemberId()).departmentId(attendance.getDepartmentId()).attendanceDate(attendance.getAttendanceDate()).
//                onWork(rectifyAttendanceDTO.getOnWork()).offWork(rectifyAttendanceDTO.getOffWork()).memo(rectifyAttendanceDTO.getMemo()).targetId(attendance.getId()).build();
//    }
//


}
