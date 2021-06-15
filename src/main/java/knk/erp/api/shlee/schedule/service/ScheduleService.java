package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.repository.ScheduleRepository;
import knk.erp.api.shlee.schedule.specification.SS;
import knk.erp.api.shlee.schedule.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final ScheduleUtil util;

    public RES_createSchedule createSchedule(ScheduleDTO scheduleDTO) {
        try {
            setMemberIdAndDepartmentId(scheduleDTO);
            scheduleRepository.save(scheduleDTO.toEntity());
            return new RES_createSchedule("CS001");
        } catch (Exception e) {
            return new RES_createSchedule("CS002", e.getMessage());
        }
    }

    public RES_readScheduleList readScheduleList(String viewOption) {
        try {
            String memberId = getMemberId();
            Long departmentId = getDepartmentId(memberId);
            List<Schedule> scheduleList = new ArrayList<>();
            if (viewOption.isEmpty()) {
                scheduleList.addAll(scheduleRepository.findAll(SS.MID(memberId).and(SS.DFS())));
            }
            else {
                scheduleList.addAll(scheduleRepository.findAll(SS.DFS().and(SS.VOP(viewOption, memberId, departmentId))));
            }

            return new RES_readScheduleList("RSL001", util.ScheduleListToDTO(scheduleList));
        } catch (Exception e) {
            return new RES_readScheduleList("RSL002", e.getMessage());
        }
    }

    public RES_readScheduleDetail readScheduleDetail(Long sid) {
        try {
            Schedule schedule = scheduleRepository.getOne(sid);
            return new RES_readScheduleDetail("RSD001", util.ScheduleToDTO(schedule));
        } catch (Exception e) {
            return new RES_readScheduleDetail("RSD002", e.getMessage());
        }
    }

    public RES_updateSchedule updateSchedule(Long sid, ScheduleDTO scheduleDTO) {
        try {
            String memberId = getMemberId();
            Schedule schedule = scheduleRepository.getOne(sid);

            if (!memberId.equals(schedule.getMemberId())) return new RES_updateSchedule("US003");

            util.DTOTOSchedule(schedule, scheduleDTO);
            scheduleRepository.save(schedule);
            return new RES_updateSchedule("US001");
        } catch (Exception e) {
            return new RES_updateSchedule("US002", e.getMessage());
        }
    }

    public RES_deleteSchedule deleteSchedule(Long sid) {
        try {
            String memberId = getMemberId();
            Schedule schedule = scheduleRepository.getOne(sid);

            if (!memberId.equals(schedule.getMemberId())) return new RES_deleteSchedule("DS003");

            schedule.setDeleted(true);
            scheduleRepository.save(schedule);
            return new RES_deleteSchedule("DS001");

        } catch (Exception e) {
            return new RES_deleteSchedule("DS002", e.getMessage());
        }
    }

    //옵션으로 페이지정보 생성
    private PageRequest getPageRequest(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "startDate");
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    //뷰 옵션 체크
    private boolean parseViewOption(String viewOption, String target) {
        return viewOption.contains(target);
    }

    //ScheduleDTO 에 맴버 아이디 및 부서아이디 입력
    private void setMemberIdAndDepartmentId(ScheduleDTO scheduleDTO) {
        String memberId = getMemberId();
        scheduleDTO.setMemberId(memberId);
        scheduleDTO.setDepartmentId(getDepartmentId(memberId));
    }

    //맴버 아이디로 부서 아이디 가져오기
    private Long getDepartmentId(String memberId) {
        try {
            return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId).getDepartment().getId();
        } catch (Exception e) {
            return -1L;
        }
    }

    //권한 정보 얻어 맴버 아이디 가져오기
    private String getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}


