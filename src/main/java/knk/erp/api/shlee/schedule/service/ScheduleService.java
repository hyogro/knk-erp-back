package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.schedule.dto.Schedule.*;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.repository.ScheduleRepository;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.schedule.specification.SS;
import knk.erp.api.shlee.schedule.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final ScheduleUtil util;

    public ResponseCM createSchedule(ScheduleDTO scheduleDTO) {
        try {
            Schedule schedule = scheduleDTO.toEntity();
            schedule.setAuthor(getMember());

            scheduleRepository.save(schedule);
            return new ResponseCM("CS001");
        } catch (Exception e) {
            return new ResponseCM("CS002", e.getMessage());
        }
    }

    public ResponseCMDL readScheduleList(String viewOption) {
        try {
            String memberId = getMemberId();
            Long departmentId = Objects.requireNonNull(getMember()).getDepartment().getId();

            List<Schedule> scheduleList = (viewOption.isEmpty())
                    ? scheduleRepository.findAll(SS.mid(memberId).and(SS.delFalse()))
                    : scheduleRepository.findAll(SS.delFalse().and(SS.viewOption(viewOption, memberId, departmentId)));
            return new ResponseCMDL("RSL001", util.ScheduleListToDTO(scheduleList));
        } catch (Exception e) {
            return new ResponseCMDL("RSL002", e.getMessage());
        }
    }

    public ResponseCMD readScheduleDetail(Long sid) {
        try {
            Schedule schedule = scheduleRepository.getOne(sid);
            return new ResponseCMD("RSD001", new ScheduleDetailData(schedule));
        } catch (Exception e) {
            return new ResponseCMD("RSD002", e.getMessage());
        }
    }

    public ResponseCM updateSchedule(Long sid, ScheduleDTO scheduleDTO) {
        try {
            String mid = getMemberId();
            Schedule schedule = scheduleRepository.getOne(sid);

            if (!checkAuth(schedule, mid)) return new ResponseCM("US003");

            util.DTOTOSchedule(schedule, scheduleDTO);
            scheduleRepository.save(schedule);
            return new ResponseCM("US001");
        } catch (Exception e) {
            return new ResponseCM("US002", e.getMessage());
        }
    }

    public ResponseCM deleteSchedule(Long sid) {
        try {
            String mid = getMemberId();
            Schedule schedule = scheduleRepository.getOne(sid);

            if (!checkAuth(schedule, mid)) return new ResponseCM("DS003");

            schedule.setDeleted(true);
            scheduleRepository.save(schedule);
            return new ResponseCM("DS001");

        } catch (Exception e) {
            return new ResponseCM("DS002", e.getMessage());
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

    //일정, 맴버아이디로 작성자권한 조회
    private boolean checkAuth(Schedule schedule, String mid){
        return schedule.getAuthor().getMemberId().equals(mid);
    }

}


