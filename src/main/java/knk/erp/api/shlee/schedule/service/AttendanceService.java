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
    public RES_onWork onWork(AttendanceDTO attendanceDTO, String token){
        try {
            String memberId = attendanceDTO.getMemberId();
            LocalDateTime now = LocalDateTime.now();

            //실패 - 기존 출근기록 있으면 리턴
            boolean isOnWorked = attendanceRepository.countByAttendanceDateAndMemberIdAndDeletedIsFalse(now.toLocalDate(), memberId) != 0;
            if(isOnWorked) return new RES_onWork("ON002");

            //실패 - 본인이 아니면 생성불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(memberId);
            if(!isOwner) return new RES_onWork("ON004");

            //성공 - 기존 출근기록 없으면 생성 후 응답
            attendanceDTO.setAttendanceDate(now.toLocalDate());
            attendanceDTO.setOnWork(now);
            attendanceRepository.save(attendanceDTO.toEntity());
            return new RES_onWork("ON001");

        }catch (Exception e){
            //실패 - Exception 발생
            return new RES_onWork("ON003", e.getMessage());
        }
    }

    //퇴근 기록
    public RES_offWork offWork(AttendanceDTO attendanceDTO, String token){
        try {
            LocalDateTime now = LocalDateTime.now();

            //실패 - 기존 퇴근기록 있으면 수정불가
            Attendance attendance = attendanceRepository.getOne(attendanceDTO.getId());
            if(attendance.getOffWork() != null) return new RES_offWork("OFF003");

            //실패 - 본인이 아니면 생성불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(attendance.getMemberId());
            if(!isOwner) return new RES_offWork("OFF004");

            //성공 - 기존 퇴근기록 없으면 생성 후 응답
            attendance.setOffWork(now);
            attendanceRepository.save(attendance);
            return new RES_offWork("OFF001");
        }catch (Exception e){
            //실패 - Exception 발생
            return new RES_offWork("OFF002", e.getMessage());
        }
    }

    //출, 퇴근기록 조회
    public RES_readAttendanceList readAttendanceList(AttendanceDTO attendanceDTO, String token){
        try {
            //실패 - 본인이 아니면 조회불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(attendanceDTO.getMemberId());
            if(!isOwner) return new RES_readAttendanceList("RAL003");

            //성공 - 삭제되지 않은 기록 중 본인의 것만 조회
            String memberId = attendanceDTO.getMemberId();
            List<Attendance> attendanceList = attendanceRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
            return new RES_readAttendanceList("RAL001", util.AttendanceListToDTO(attendanceList));
        }catch (Exception e){
            //실패 - Exception 발생
            return new RES_readAttendanceList("RAL002", e.getMessage());
        }
    }

    //출,퇴근기록 정정 요청 -> 출근 혹은 출,퇴근을 못찍은 경우 -> 신규 생성
    public RES_createRectifyAttendance createRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO, String token){
        try {
            //실패 - 본인이 아니면 수정불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(rectifyAttendanceDTO.getMemberId());
            if(!isOwner) return new RES_createRectifyAttendance("CRA003");

            //성공 - 생성 후 응답
            rectifyAttendanceRepository.save(rectifyAttendanceDTO.toEntity());
            return new RES_createRectifyAttendance("CRA001");
        }catch (Exception e){
            //실패 - Exception 발생
            return new RES_createRectifyAttendance("CRA002", e.getMessage());
        }
    }

    //퇴근기록 정정 요청 -> 퇴근을 못찍은 경우 -> 기존것 수정
    public RES_updateRectifyAttendance updateRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO, String token){
        try {
            //실패 - 본인이 아니면 수정불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(rectifyAttendanceDTO.getMemberId());
            if(!isOwner) return new RES_updateRectifyAttendance("URA003");

            //성공 - 수정 후 응답
            RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rectifyAttendanceDTO.getId());
            rectifyAttendance.setOffWork(rectifyAttendanceDTO.getOffWork());
            rectifyAttendanceRepository.save(rectifyAttendance);
            return new RES_updateRectifyAttendance("URA001");
        }catch (Exception e){
            //실패 - Exception 발생
            return new RES_updateRectifyAttendance("URA002", e.getMessage());
        }
    }

    //출,퇴근 정정요청목록 조회
    public RES_readRectifyAttendanceList readRectifyAttendanceList(RectifyAttendanceDTO rectifyAttendanceDTO, String token){
        try {
            //실패 - 본인이 아니면 조회불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(rectifyAttendanceDTO.getMemberId());
            if(!isOwner) return new RES_readRectifyAttendanceList("RRAL003");

            //성공 - 삭제되지 않은 기록 중 본인의 것만 조회
            List<RectifyAttendance> rectifyAttendanceList = rectifyAttendanceRepository.findAllByMemberIdAndDeletedIsFalse(rectifyAttendanceDTO.getMemberId());
            return new RES_readRectifyAttendanceList("RRAL001", util.RectifyAttendanceListToDTO(rectifyAttendanceList));
        }catch (Exception e){
            //실패 - Exception 발생
            return new RES_readRectifyAttendanceList("RRAL002", e.getMessage());
        }
    }

    //출,퇴근 정정요청 삭제
    public RES_deleteRectifyAttendance deleteRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO, String token){
        try {
            //실패 - 본인이 아니면 삭제불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(rectifyAttendanceDTO.getMemberId());
            if(!isOwner) return new RES_deleteRectifyAttendance("DRA003");

            //성공 - 삭제
            RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rectifyAttendanceDTO.getId());
            rectifyAttendance.setDeleted(true);
            rectifyAttendanceRepository.save(rectifyAttendance);
            return new RES_deleteRectifyAttendance("DRA001");
        }catch (Exception e){
            //실패 - Exception 발생
            return new RES_deleteRectifyAttendance("DRA002", e.getMessage());
        }
    }

}


