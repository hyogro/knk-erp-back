package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.repository.ScheduleRepository;
import knk.erp.api.shlee.schedule.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ScheduleRepository scheduleRepository;
    private final ScheduleUtil util;

    public RES_createSchedule createSchedule(ScheduleDTO scheduleDTO){
        try {
            scheduleRepository.save(scheduleDTO.toEntity());
            return new RES_createSchedule("CS001");
        }catch (Exception e){
            return new RES_createSchedule("CS002", e.getCause().toString());
        }
    }

    public RES_readScheduleList readScheduleList(){
        try {
            List<Schedule> scheduleList = scheduleRepository.findAll();
            return new RES_readScheduleList("RSL001", util.ScheduleListToDTO(scheduleList));
        }
        catch (Exception e){
            return new RES_readScheduleList("RSL002", e.getMessage());
        }
    }

    public RES_readScheduleDetail readScheduleDetail(ScheduleDTO scheduleDTO){
        try {
            Schedule schedule = scheduleRepository.getOne(scheduleDTO.getId());
            return new RES_readScheduleDetail("RSD001", util.ScheduleToDTO(schedule));
        }
        catch (Exception e){
            return new RES_readScheduleDetail("RSD002", e.getMessage());
        }
    }

    public RES_updateSchedule updateSchedule(ScheduleDTO scheduleDTO){
        try {
            Schedule schedule = scheduleRepository.getOne(scheduleDTO.getId());
            util.DTOTOSchedule(schedule, scheduleDTO);

            scheduleRepository.save(schedule);
            return new RES_updateSchedule("US001");
        }catch (Exception e){
            return new RES_updateSchedule("US002", e.getMessage());
        }
    }

    public RES_deleteSchedule deleteSchedule(ScheduleDTO scheduleDTO){
        try {
            scheduleRepository.deleteById(scheduleDTO.getId());
            return new RES_deleteSchedule("DS001");
        }catch (Exception e){
            return new RES_deleteSchedule("DS002", e.getMessage());
        }
    }



}


