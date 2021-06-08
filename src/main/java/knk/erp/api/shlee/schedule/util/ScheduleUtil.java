package knk.erp.api.shlee.schedule.util;

import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleUtil {
    public List<ScheduleDTO> ScheduleListToDTO(List<Schedule> scheduleList){
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for(Schedule schedule : scheduleList){
            scheduleDTOList.add(new ScheduleDTO(schedule));
        }
        return scheduleDTOList;
    }
    public ScheduleDTO ScheduleToDTO(Schedule schedule){
        return new ScheduleDTO(schedule);
    }
    public void DTOTOSchedule(Schedule schedule, ScheduleDTO scheduleDTO){
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setMemo(scheduleDTO.getMemo());
        schedule.setViewOption(scheduleDTO.getViewOption());
        schedule.setStartDate(scheduleDTO.getStartDate());
        schedule.setEndDate(scheduleDTO.getEndDate());
        schedule.setMemberId(scheduleDTO.getMemberId());
        schedule.setDepartmentId(scheduleDTO.getDepartmentId());
    }

}
