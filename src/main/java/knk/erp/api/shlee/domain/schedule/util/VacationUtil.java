package knk.erp.api.shlee.domain.schedule.util;

import knk.erp.api.shlee.domain.schedule.dto.Vacation.AddVacationListData;
import knk.erp.api.shlee.domain.schedule.dto.Vacation.VacationListData;
import knk.erp.api.shlee.domain.schedule.entity.AddVacation;
import knk.erp.api.shlee.domain.schedule.entity.Vacation;
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
