package knk.erp.api.shlee.schedule.util;

import knk.erp.api.shlee.schedule.dto.Attendance.AttendanceDTO;
import knk.erp.api.shlee.schedule.dto.Attendance.RectifyAttendanceDTO;
import knk.erp.api.shlee.schedule.dto.Vacation.AddVacationListData;
import knk.erp.api.shlee.schedule.dto.Vacation.VacationDTO;
import knk.erp.api.shlee.schedule.dto.Vacation.VacationListData;
import knk.erp.api.shlee.schedule.entity.AddVacation;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VacationUtil {

    public List<Object> AddVacationListToDTO(List<AddVacation> addVacationList){
        List<Object> vacationDTOList = new ArrayList<>();
        for(AddVacation addVacation : addVacationList){
            vacationDTOList.add(new AddVacationListData(addVacation));
        }
        return vacationDTOList;
    }

    public List<Object> VacationListToDTO(List<Vacation> vacationList){
        List<Object> vacationDTOList = new ArrayList<>();
        for(Vacation vacation : vacationList){
            vacationDTOList.add(new VacationListData(vacation));
        }
        return vacationDTOList;
    }
}
