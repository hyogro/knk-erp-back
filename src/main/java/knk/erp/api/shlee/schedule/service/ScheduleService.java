package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.repository.ScheduleRepository;
import knk.erp.api.shlee.schedule.responseEntity.schedule.*;
import knk.erp.api.shlee.schedule.specification.SS;
import knk.erp.api.shlee.schedule.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final ScheduleUtil util;

    @Transactional
    public void test() {
        for (int i = 0; i < 100; i++) {
            String title = "부하테스트 제목_" + i;
            String memo = "부하테스트 제목_" + i;
            String viewOption = (i % 3 == 0) ? "all" : (i % 3 == 2) ? "dep" : "own";
            int rand1 = (int)(Math.random()*100);
            int rand2 = (int)(Math.random()*9);

            LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2021,6, rand1%20+1), LocalTime.of(9, 30));
            LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2021,6, rand1%20+1 + rand2), LocalTime.of(9, 30));
            ScheduleDTO scheduleDTO = new ScheduleDTO(null, title, memo, viewOption, startDate, endDate);

            System.out.println(scheduleDTO.toString());
            Schedule schedule = scheduleDTO.toEntity();
            schedule.setAuthor(getMember());
            scheduleRepository.save(schedule);
        }
    }

    public RES_createSchedule createSchedule(ScheduleDTO scheduleDTO) {
        try {
            Schedule schedule = scheduleDTO.toEntity();
            schedule.setAuthor(getMember());
            scheduleRepository.save(schedule);
            return new RES_createSchedule("CS001");
        } catch (Exception e) {
            return new RES_createSchedule("CS002", e.getMessage());
        }
    }

    public RES_readScheduleList readScheduleList(String viewOption) {
        try {
            String memberId = getMemberId();
            Long departmentId = Objects.requireNonNull(getMember()).getDepartment().getId();
            List<Schedule> scheduleList = (viewOption.isEmpty())
                    ? scheduleRepository.findAll(SS.mid(memberId).and(SS.delFalse()))
                    : scheduleRepository.findAll(SS.delFalse().and(SS.viewOption(viewOption, memberId, departmentId)));

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

            if (!memberId.equals(schedule.getAuthor().getMemberId())) return new RES_updateSchedule("US003");

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

            if (!memberId.equals(schedule.getAuthor().getMemberId())) return new RES_deleteSchedule("DS003");

            schedule.setDeleted(true);
            scheduleRepository.save(schedule);
            return new RES_deleteSchedule("DS001");

        } catch (Exception e) {
            return new RES_deleteSchedule("DS002", e.getMessage());
        }
    }

    //권한 정보 얻어 맴버 아이디 가져오기
    private String getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private Member getMember() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
        } catch (Exception e) {
            return null;
        }
    }

}


