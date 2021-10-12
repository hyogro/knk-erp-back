package knk.erp.api.shlee.domain.schedule.service;

import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.domain.schedule.dto.Schedule.*;
import knk.erp.api.shlee.domain.schedule.entity.Schedule;
import knk.erp.api.shlee.domain.schedule.repository.ScheduleRepository;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.domain.schedule.specification.SS;
import knk.erp.api.shlee.domain.schedule.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final ScheduleUtil util;

    public void createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTO.toEntity();
        schedule.setAuthor(getMember());

        scheduleRepository.save(schedule);
    }

    public List<Object> readScheduleList(String viewOption, LocalDateTime startDate, LocalDateTime endDate) {
        String memberId = getMemberId();
        Long departmentId = Objects.requireNonNull(getMember()).getDepartment().getId();

        List<Schedule> scheduleList = (viewOption.isEmpty())
                ? scheduleRepository.findAll(SS.mid(memberId).and(SS.delFalse()).and(SS.startDateAfter(startDate)).and(SS.endDateBefore(endDate)))
                : scheduleRepository.findAll(SS.delFalse().and(SS.viewOption(viewOption, memberId, departmentId)).and(SS.startDateAfter(startDate)).and(SS.endDateBefore(endDate)));

        return util.ScheduleListToDTO(scheduleList);
    }

    public ScheduleDetailData readScheduleDetail(Long sid) {
        Schedule schedule = scheduleRepository.getOne(sid);
        return new ScheduleDetailData(schedule);
    }


    public void updateSchedule(Long sid, ScheduleDTO scheduleDTO) {
        //TODO: 본인의 일정이 아닌경우 예외처리 하기
        Schedule schedule = scheduleRepository.getOne(sid);

        util.DTOTOSchedule(schedule, scheduleDTO);
        scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long sid) {
        //TODO: 본인의 일정이 아닌경우 예외처리 하기
        Schedule schedule = scheduleRepository.getOne(sid);

        schedule.setDeleted(true);
        scheduleRepository.save(schedule);
    }

    public List<Object> readAnniversaryList(LocalDate startDate, LocalDate endDate) {
            List<Member> memberList = memberRepository.findAllByBirthDateIsNotNullAndDeletedFalse();

            //TODO: specification 쿼리로 대체
            memberList.removeIf(i -> !birthDateBetweenCheck(startDate, endDate, i.getBirthDate()));
            return util.AnniversaryListToDTO(startDate.getYear(), memberList);
    }

    //월, 일만 비교하기
    private boolean birthDateBetweenCheck(LocalDate startDate, LocalDate endDate, LocalDate birthDate) {
        LocalDate nowBirthDate = LocalDate.of(startDate.getYear(), birthDate.getMonthValue(), birthDate.getDayOfMonth());
        return startDate.equals(nowBirthDate) || endDate.equals(nowBirthDate) || (nowBirthDate.isAfter(startDate) && nowBirthDate.isBefore(endDate));
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
    private boolean checkAuth(Schedule schedule, String mid) {
        return schedule.getAuthor().getMemberId().equals(mid);
    }

}


