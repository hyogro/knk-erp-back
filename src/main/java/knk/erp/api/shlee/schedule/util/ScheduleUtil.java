package knk.erp.api.shlee.schedule.util;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDetailData;
import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleListData;
import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleUtil {

    public List<Object> AnniversaryListToDTO(int year, List<Member> memberList){
        List<Object> scheduleDTOList = new ArrayList<>();
        for(Member member : memberList){
            scheduleDTOList.add(new ScheduleListData(year, member));
        }
        return scheduleDTOList;
    }

    public List<Object> ScheduleListToDTO(List<Schedule> scheduleList){
        List<Object> scheduleDTOList = new ArrayList<>();
        for(Schedule schedule : scheduleList){
            scheduleDTOList.add(new ScheduleListData(schedule));
        }
        return scheduleDTOList;
    }

    public void DTOTOSchedule(Schedule schedule, ScheduleDTO scheduleDTO){
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setMemo(scheduleDTO.getMemo());
        schedule.setViewOption(scheduleDTO.getViewOption());
        schedule.setStartDate(scheduleDTO.getStartDate());
        schedule.setEndDate(scheduleDTO.getEndDate());
        /*
        schedule.setMemberId(scheduleDTO.getMemberId());
        schedule.setDepartmentId(scheduleDTO.getDepartmentId());
         */
    }

}
