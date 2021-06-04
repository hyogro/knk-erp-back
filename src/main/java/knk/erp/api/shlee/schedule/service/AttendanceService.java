package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.common.jwt.TokenProvider;
import knk.erp.api.shlee.schedule.dto.Attendance.*;
import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.repository.AttendanceRepository;
import knk.erp.api.shlee.schedule.repository.RectifyAttendanceRepository;
import knk.erp.api.shlee.schedule.repository.ScheduleRepository;
import knk.erp.api.shlee.schedule.util.AttendanceUtil;
import knk.erp.api.shlee.schedule.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AttendanceRepository attendanceRepository;
    private final RectifyAttendanceRepository rectifyAttendanceRepository;
    private final TokenProvider tokenProvider;
    private final AttendanceUtil util;

    //출근 기록
    public RES_onWork onWork(AttendanceDTO attendanceDTO){
        try {
            String memberId = attendanceDTO.getMemberId();
            LocalDateTime now = LocalDateTime.now();

            //기존 출근기록 있으면 리턴
            boolean isOnWorked = attendanceRepository.countByAttendanceDateAndMemberIdAndDeletedIsFalse(now.toLocalDate(), memberId) != 0;
            if(isOnWorked) return new RES_onWork("ON002");

            //기존 출근기록 없으면 생성 후 리턴
            attendanceDTO.setAttendanceDate(now.toLocalDate());
            attendanceDTO.setOnWork(now);
            attendanceRepository.save(attendanceDTO.toEntity());
            return new RES_onWork("ON001");

        }catch (Exception e){
            return new RES_onWork("ON003", e.getMessage());
        }
    }

    //퇴근 기록
    public RES_offWork offWork(AttendanceDTO attendanceDTO){
        try {
            LocalDateTime now = LocalDateTime.now();

            //기존 퇴근기록 있으면 리턴
            Attendance attendance = attendanceRepository.getOne(attendanceDTO.getId());
            if(attendance.getOffWork() != null) return new RES_offWork("OFF003");

            //기존 퇴근기록 없으면 생성 후 리턴
            attendance.setOffWork(now);
            attendanceRepository.save(attendance);
            return new RES_offWork("OFF001");
        }catch (Exception e){
            return new RES_offWork("OFF002", e.getMessage());
        }
    }

    //출, 퇴근기록 조회
    public RES_readAttendanceList readAttendanceList(AttendanceDTO attendanceDTO){
        try {
            String memberId = attendanceDTO.getMemberId();
            List<Attendance> attendanceList = attendanceRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
            return new RES_readAttendanceList("RAL001", util.AttendanceListToDTO(attendanceList));
        }catch (Exception e){
            return new RES_readAttendanceList("RAL002", e.getMessage());
        }
    }

    //출근기록 정정 요청 -> 신규 생성 요청
    public RES_createRectifyAttendance createRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO){
        try {
            rectifyAttendanceRepository.save(rectifyAttendanceDTO.toEntity());
            return new RES_createRectifyAttendance("CRA001");
        }catch (Exception e){
            return new RES_createRectifyAttendance("CRA002", e.getMessage());
        }
    }

    //퇴근기록 정정 요청 -> 기존
    public RES_updateRectifyAttendance updateRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO){
        try {
            RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rectifyAttendanceDTO.getId());
            rectifyAttendance.setOffWork(rectifyAttendanceDTO.getOffWork());
            rectifyAttendanceRepository.save(rectifyAttendance);
            return new RES_updateRectifyAttendance("URA001");
        }catch (Exception e){
            return new RES_updateRectifyAttendance("URA002", e.getMessage());
        }
    }

    //출근을 못찍은 경우
    //퇴근을 못찍은 경우
    //출,퇴근을 못찍은 경우


}


