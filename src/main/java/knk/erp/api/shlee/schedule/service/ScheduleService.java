package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.CommonUtil;
import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.repository.ScheduleRepository;
import knk.erp.api.shlee.schedule.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final ScheduleUtil util;
    private final CommonUtil commonUtil;

    public RES_createSchedule createSchedule(ScheduleDTO scheduleDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            scheduleDTO.setMemberId(memberId);
            scheduleDTO.setDepartmentId(getDepartmentIdByMemberId(memberId));
            scheduleRepository.save(scheduleDTO.toEntity());
            return new RES_createSchedule("CS001");
        } catch (Exception e) {
            return new RES_createSchedule("CS002", e.getMessage());
        }
    }

    public RES_readScheduleList readScheduleList(REQ_readScheduleListOption option) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            List<Schedule> scheduleList = new ArrayList<>();
            Sort sort = Sort.by(Sort.Direction.ASC, "startDate");

            if (option.getViewOption().contains("all")) {
                scheduleList.addAll(scheduleRepository.findAllByViewOptionAndDeletedIsFalse("all"
                        , PageRequest.of(option.getPage(), option.getSize(), sort)).toList());
            }
            if (option.getViewOption().contains("dep")) {
                Long departmentId = getDepartmentIdByMemberId(memberId);
                scheduleList.addAll(scheduleRepository.findAllByViewOptionAndDepartmentIdAndDeletedIsFalse("dep"
                        , departmentId, PageRequest.of(option.getPage(), option.getSize(), sort)).toList());
            }
            if (option.getViewOption().contains("own")) {
                scheduleList.addAll(scheduleRepository.findAllByViewOptionAndMemberIdAndDeletedIsFalse("own"
                        , memberId, PageRequest.of(option.getPage(), option.getSize(), sort)).toList());
            }

            return new RES_readScheduleList("RSL001", util.ScheduleListToDTO(scheduleList));
        } catch (Exception e) {
            return new RES_readScheduleList("RSL002", e.getMessage());
        }
    }

    public RES_readScheduleDetail readScheduleDetail(ScheduleDTO scheduleDTO) {
        try {

            Schedule schedule = scheduleRepository.getOne(scheduleDTO.getId());
            return new RES_readScheduleDetail("RSD001", util.ScheduleToDTO(schedule));
        } catch (Exception e) {
            return new RES_readScheduleDetail("RSD002", e.getMessage());
        }
    }

    public RES_updateSchedule updateSchedule(ScheduleDTO scheduleDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();

            Schedule schedule = scheduleRepository.getOne(scheduleDTO.getId());

            //실패 - 본인이 아니면 수정불가
            if (!memberId.equals(schedule.getMemberId())) return new RES_updateSchedule("US003");

            util.DTOTOSchedule(schedule, scheduleDTO);

            scheduleRepository.save(schedule);
            return new RES_updateSchedule("US001");
        } catch (Exception e) {
            return new RES_updateSchedule("US002", e.getMessage());
        }
    }

    public RES_deleteSchedule deleteSchedule(ScheduleDTO scheduleDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            Schedule schedule = scheduleRepository.getOne(scheduleDTO.getId());
            //실패 - 본인이 아니면 삭제불가
            if (!memberId.equals(schedule.getMemberId())) return new RES_deleteSchedule("DS003");

            schedule.setDeleted(true);
            scheduleRepository.save(schedule);
            return new RES_deleteSchedule("DS001");

        } catch (Exception e) {
            return new RES_deleteSchedule("DS002", e.getMessage());
        }
    }

    public RES_readScheduleList readIndexScheduleList() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();

            LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
            List<Schedule> scheduleList = scheduleRepository.findAllByDeletedIsFalseAndMemberIdAndEndDateAfter(memberId, today, PageRequest.of(0, 5)).toList();
            return new RES_readScheduleList("RSL001", util.ScheduleListToDTO(scheduleList));
        } catch (Exception e) {
            return new RES_readScheduleList("RSL002", e.getMessage());
        }
    }

    //맴버 아이디로 부서 아이디 가져오기
    private Long getDepartmentIdByMemberId(String memberId) {
        try {
            return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId).getDepartment().getId();
        } catch (Exception e) {
            return -1L;
        }
    }

}


