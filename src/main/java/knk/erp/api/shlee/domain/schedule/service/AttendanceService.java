package knk.erp.api.shlee.domain.schedule.service;

import knk.erp.api.shlee.common.util.EntityUtil;
import knk.erp.api.shlee.domain.account.entity.Department;
import knk.erp.api.shlee.domain.account.entity.DepartmentRepository;
import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.AuthorityUtil;
import knk.erp.api.shlee.domain.schedule.dto.Attendance.*;
import knk.erp.api.shlee.domain.schedule.entity.Attendance;
import knk.erp.api.shlee.domain.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.domain.schedule.entity.Vacation;
import knk.erp.api.shlee.domain.schedule.repository.AttendanceRepository;
import knk.erp.api.shlee.domain.schedule.repository.RectifyAttendanceRepository;
import knk.erp.api.shlee.domain.schedule.repository.VacationRepository;
import knk.erp.api.shlee.domain.schedule.specification.AS;
import knk.erp.api.shlee.domain.schedule.specification.RAS;
import knk.erp.api.shlee.domain.schedule.specification.VS;
import knk.erp.api.shlee.domain.schedule.util.AttendanceUtil;
import knk.erp.api.shlee.exception.exceptions.attendance.AttendanceExistException;
import knk.erp.api.shlee.exception.exceptions.attendance.AttendanceNotExistException;
import knk.erp.api.shlee.exception.exceptions.attendance.AttendanceOffWorkExistException;
import knk.erp.api.shlee.exception.exceptions.attendance.RectifyAttendanceExistException;
import knk.erp.api.shlee.exception.exceptions.common.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final RectifyAttendanceRepository rectifyAttendanceRepository;
    private final VacationRepository vacationRepository;
    private final AttendanceUtil dtoUtil;
    private final AuthorityUtil authorityUtil;

    /**
     * ?????? ?????? ????????? ?????? ?????????, ?????? ??????????????? ??????
     **/
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    //?????? ??????
    @Transactional
    public void onWork(String uuid) {
        //?????? ??????????????? ????????? ??????
        throwIfTodayAttendanceExist();

        //?????? ??????????????? ????????? ??????
        throwIfRectifyAttendanceExist();

        Attendance attendance = AttendanceDto
                .builder()
                .uuid(uuid)
                .build()
                .toEntity();
        attendance.doOnWork();

        Member author = EntityUtil.getInstance().getMember(memberRepository);
        attendance.setAuthor(author);

        attendanceRepository.save(attendance);
    }

    private Optional<Attendance> getAttendanceOpt(LocalDate date) {
        String memberId = EntityUtil.getInstance().getMemberId();

        return attendanceRepository.findOne(AS.searchWithDateAndMemberId(date, memberId));
    }

    private Optional<RectifyAttendance> getTodayRectifyAttendanceOpt() {

        LocalDate today = LocalDate.now();
        String memberId = EntityUtil.getInstance().getMemberId();

        return rectifyAttendanceRepository.findOne(RAS.searchWithDateAndMemberId(today, memberId));
    }

    private void throwIfTodayAttendanceExist() {
        LocalDate today = LocalDate.now();
        Optional<Attendance> todayAttendanceOpt = getAttendanceOpt(today);
        if (todayAttendanceOpt.isPresent()) {
            throw new AttendanceExistException();
        }
    }

    private void throwIfRectifyAttendanceExist() {
        Optional<RectifyAttendance> rectifyAttendance = getTodayRectifyAttendanceOpt();

        if (rectifyAttendance.isPresent()) {
            throw new RectifyAttendanceExistException();
        }
    }

    //?????? ??????
    @Transactional
    public void offWork() {
        //??????????????? ????????????, ???????????? ?????? ??? ??????
        Attendance attendance = throwIfAttendanceNotExistOrReturn();

        //?????? ??????????????? ????????? ??????
        throwIfAlreadyOffWorkExist(attendance);

        attendance.doOffWork();
        attendanceRepository.save(attendance);
    }

    private Attendance throwIfAttendanceNotExistOrReturn() {
        LocalDate today = LocalDate.now();
        return getAttendanceOpt(today).orElseThrow(AttendanceNotExistException::new);
    }

    private void throwIfAlreadyOffWorkExist(Attendance attendance) {
        if (attendance.getOffWork() != null) {
            throw new AttendanceOffWorkExistException();
        }
    }

    //???, ???????????? ??????
    @Transactional
    public List<AttendanceDto> readAttendanceList(LocalDate startDate, LocalDate endDate) {
        String memberId = EntityUtil.getInstance().getMemberId();
        List<Attendance> attendanceList = attendanceRepository.findAll(AS.searchWithDateBetween(memberId, startDate, endDate));
        return attendanceList.stream().map(AttendanceDto::new).collect(Collectors.toList());

    }

    //?????? ???,?????? ?????? ??????
    @Transactional
    public AttendanceDto readAttendance(Long aid) {
        return new AttendanceDto(attendanceRepository.getOne(aid));
    }

    private void throwIfRectifyAttendanceExist(LocalDate attendanceDate, String memberId) {
        Optional<RectifyAttendance> rectifyAttendanceOptional = rectifyAttendanceRepository.findOne(RAS.searchWithDateAndMemberId(attendanceDate, memberId));

        if (rectifyAttendanceOptional.isPresent()) {
            throw new RectifyAttendanceExistException();
        }

    }

    private void throwIfAttendanceExist(LocalDate attendanceDate) {
        if (getAttendanceOpt(attendanceDate).isPresent()) {
            throw new AttendanceExistException();
        }
    }

    //???,???????????? ?????? ?????? -> ???????????? ?????? ??????
    @Transactional
    public void createRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO) {
        String memberId = EntityUtil.getInstance().getMemberId();
        LocalDate attendanceDate = rectifyAttendanceDTO.getAttendanceDate();

        //???????????? ??????????????? ???????????? ????????????(?????????????????? ???????????? ???)
        throwIfAttendanceExist(attendanceDate);

        //???????????? ???????????? ?????? ???????????? ????????????
        throwIfRectifyAttendanceExist(attendanceDate, memberId);

        //?????? - ?????? ??? ??????
        RectifyAttendance rectifyAttendance = rectifyAttendanceDTO.toEntity();
        System.out.println(rectifyAttendance.getAttendanceDate());
        rectifyAttendance.setAuthor(EntityUtil.getInstance().getMember(memberRepository));

        //TODO: ????????????
        //????????? ?????? 1,2??? ???????????? ??????
        rectifyApproved(rectifyAttendance);
        RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);

        //TODO: ????????????
        //1,2??? ????????? ???,?????? ?????? ??????
        rectifyToAttendance(done);
    }

    private Attendance throwIfAttendanceNotExist(Long attendanceId) {
        Optional<Attendance> attendanceOptional = attendanceRepository.findOne(AS.searchWithAttendanceId(attendanceId));
        if (!attendanceOptional.isPresent()) {
            throw new AttendanceNotExistException();
        }
        return attendanceOptional.get();
    }

    //???,???????????? ?????? ?????? -> ????????? ???????????? ???????????? ??????
    @Transactional
    public void updateRectifyAttendance(Long aid, RectifyAttendanceDTO rectifyAttendanceDTO) {


        //?????? ??????????????? ???????????? ????????? ????????????
        Attendance attendance = throwIfAttendanceNotExist(aid);
        attendance.setDeleted(true);
        attendanceRepository.save(attendance);

        //???????????? ???????????? ?????? ???????????? ????????????
        String memberId = EntityUtil.getInstance().getMemberId();
        LocalDate attendanceDate = attendance.getAttendanceDate();
        throwIfRectifyAttendanceExist(attendanceDate, memberId);

        //?????? - ?????? ??? ??????
        RectifyAttendance rectifyAttendance = rectifyAttendanceDTO.toEntity();
        rectifyAttendance.setAttendanceDate(attendance.getAttendanceDate());
        rectifyAttendance.setTargetId(aid);
        rectifyAttendance.setAuthor(EntityUtil.getInstance().getMember(memberRepository));

        //????????? ?????? 1,2??? ???????????? ??????
        rectifyApproved(rectifyAttendance);
        RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);


        //1,2??? ????????? ???,?????? ?????? ??????
        rectifyToAttendance(done);
    }

    //???,?????? ?????????????????? ??????
    @Transactional
    public List<Object> readRectifyAttendanceList() {
        String memberId = EntityUtil.getInstance().getMemberId();

        List<RectifyAttendance> rectifyAttendanceList = rectifyAttendanceRepository.findAll(RAS.searchWithMemberId(memberId));
        return dtoUtil.entityToDto(rectifyAttendanceList);
    }

    //???,?????? ?????????????????? ??????
    @Transactional
    public RectifyAttendanceDetailData readRectifyAttendance(Long rid) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);
        return new RectifyAttendanceDetailData(rectifyAttendance);
    }

    //???,?????? ???????????? ??????
    @Transactional
    public void deleteRectifyAttendance(Long rid) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);
        rectifyAttendance.setDeleted(true);
        rectifyAttendanceRepository.save(rectifyAttendance);
    }

    //??????????????? ???,?????? ?????????????????? ??????
    @Transactional
    public List<Object> readRectifyAttendanceListForApprove() {
        List<RectifyAttendance> rectifyAttendanceList = new ArrayList<>();

        if (authorityUtil.checkLevel() == 1) {
            throw new PermissionDeniedException();
        }
        if (authorityUtil.checkLevel() == 2) {
            Long departmentId = EntityUtil.getInstance().getDepartmentId(memberRepository);
            rectifyAttendanceList = rectifyAttendanceRepository.findAll(RAS.searchWithDepartmentIdAndApprove1IsFalse(departmentId));
        } else if (3 <= authorityUtil.checkLevel()) {
            rectifyAttendanceList = rectifyAttendanceRepository.findAll(RAS.delFalse());
        }

        return dtoUtil.entityToDto(rectifyAttendanceList);
    }

    //???,?????? ?????? ?????? ?????? 2, ?????? 3??? ?????? ??????.
    @Transactional
    public void approveRectifyAttendance(Long rid) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);
        //TODO: ????????????????????? ????????????
        //TODO: rectifyApproved(rectifyAttendance) ???????????? ?????????????????? ????????? ????????? ????????????
        rectifyApproved(rectifyAttendance);

        RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);
        rectifyToAttendance(done);
    }

    //???,?????? ???????????? ??????
    public AttendanceSummaryDTO readAttendanceSummary() {
        LocalDate today = LocalDate.now();
        LocalDateTime st = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime et = LocalDateTime.of(today, LocalTime.MAX);

        if (authorityUtil.checkLevel() == 2) {
            Department department = getDepartment();
            Long departmentId = department.getId();

            List<Attendance> onWorkList = attendanceRepository.findAll(AS.delFalse()
                    .and(AS.atteDate(today))
                    .and(AS.did(departmentId))
            );

            List<Attendance> offWorkList = attendanceRepository.findAll(AS.delFalse()
                    .and(AS.atteDate(today))
                    .and(AS.offWorked())
                    .and(AS.did(departmentId))
            );

            List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse()
                    .and(VS.did(departmentId))
                    .and(VS.vacationDateBetween(st, et))
                    .and(VS.approve1Is(true))
                    .and(VS.approve2Is(true))
            );

            List<Attendance> lateWorkList = new ArrayList<>(onWorkList);
            List<Member> yetWorkList = department.getMemberList();

            AttendanceSummaryDTO attendanceSummaryDTO = makeAttendanceSummary(onWorkList, offWorkList, vacationList, lateWorkList, yetWorkList);

            return attendanceSummaryDTO;

        } else if (3 <= authorityUtil.checkLevel()) {
            List<Attendance> onWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today).and(AS.memberDF())));
            List<Attendance> offWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today).and(AS.offWorked()).and(AS.memberDF())));
            List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.vacationDateBetween(st, et)).and(VS.approve1Is(true)).and(VS.approve2Is(true)).and(VS.memberDF()));
            List<Attendance> lateWorkList = new ArrayList<>(onWorkList);
            List<Member> yetWorkList = memberRepository.findAllByDeletedIsFalse();

            return makeAttendanceSummary(onWorkList, offWorkList, vacationList, lateWorkList, yetWorkList);

        } else {
            throw new PermissionDeniedException();
        }
    }

    //?????? ???,?????? ???????????? ??????
    @Transactional
    public AttendanceDto readAttendanceToday() {
        String memberId = EntityUtil.getInstance().getMemberId();
        LocalDate today = LocalDate.now();
        Optional<Attendance> attendance = attendanceRepository.findOne(AS.delFalse().and(AS.atteDate(today).and(AS.mid(memberId))));
        return attendance.map(AttendanceDto::new).orElseThrow(EntityNotFoundException::new);

    }

    //uuid ???????????? ???, ???????????? ??????
    @Transactional
    public List<Object> readDuplicateAttendanceList(LocalDate date) {
        List<Attendance> attendanceList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(date)));
        //List<String> uuidList = attendanceList.stream().map(Attendance::getUuid).collect(Collectors.toList());
        return dtoUtil.AttendanceUuidListToDTO(attendanceList);
    }


    //????????? ?????? ?????? ??????
    private boolean checkAttendance(Member member, List<Attendance> onWorkList) {
        for (Attendance attendance : onWorkList) {
            if (attendance.getAuthor().getMemberId().equals(member.getMemberId())) return true;
        }
        return false;
    }

    //?????? ????????? ?????? ?????? ??????
    private boolean checkVacationLate(Attendance attendance, List<Vacation> vacationList) {
        LocalDateTime onWorkTime = LocalDateTime.of(LocalDate.now(), attendance.getOnWork());
        for (Vacation vacation : vacationList) {
            if (vacation.getAuthor().equals(attendance.getAuthor())) {
                return vacation.getEndDate().isAfter(onWorkTime);
            }
        }
        return false;
    }

    //?????? ????????? ?????? ?????? ??????
    private boolean checkVacation(Member member, List<Vacation> vacationList) {
        LocalDateTime now = LocalDateTime.now();
        for (Vacation vacation : vacationList) {
            if (vacation.getAuthor().equals(member)) {
                return vacation.getStartDate().isBefore(now) && vacation.getEndDate().isAfter(now);
            }
        }
        return false;
    }

    //?????? DTO ??????
    private AttendanceSummaryDTO makeAttendanceSummary(List<Attendance> onWorkList, List<Attendance> offWorkList, List<Vacation> vacationList, List<Attendance> lateWorkList, List<Member> yetWorkList) {

        List<MemberDepartmentNameDTO> onWork, offWork, yetWork, lateWork, vacation;//??????, ?????????, ??????, ??????
        LocalTime nine = LocalTime.of(9, 0, 0);

        lateWorkList.removeIf(a -> a.getOnWork().isBefore(nine));// 9??? ?????? ????????? ?????? = ?????? ??????
        lateWorkList.removeIf(a -> checkVacationLate(a, vacationList));// ????????? ?????? = ?????? ??????

        yetWorkList.removeIf(member -> checkAttendance(member, onWorkList)); //????????? ?????? = ????????? ??????
        yetWorkList.removeIf(member -> checkVacation(member, vacationList)); // ????????? ?????? = ????????? ??????

        onWork = attendanceListToMDList(onWorkList);
        offWork = attendanceListToMDList(offWorkList);
        lateWork = attendanceListToMDList(lateWorkList);
        vacation = vacationListToMDList(vacationList);
        yetWork = memberListToMDList(yetWorkList);

        onWork.removeIf(i -> i.getMemberName().equals("?????????"));
        offWork.removeIf(i -> i.getMemberName().equals("?????????"));
        lateWork.removeIf(i -> i.getMemberName().equals("?????????"));
        vacation.removeIf(i -> i.getMemberName().equals("?????????"));
        yetWork.removeIf(i -> i.getMemberName().equals("?????????"));
        return new AttendanceSummaryDTO(onWork, offWork, yetWork, lateWork, vacation);
    }

    private List<MemberDepartmentNameDTO> attendanceListToMDList(List<Attendance> attendanceList) {
        List<MemberDepartmentNameDTO> mdList = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            mdList.add(new MemberDepartmentNameDTO(attendance));
        }
        return mdList;
    }

    private List<MemberDepartmentNameDTO> vacationListToMDList(List<Vacation> vacationList) {
        List<MemberDepartmentNameDTO> mdList = new ArrayList<>();
        for (Vacation vacation : vacationList) {
            mdList.add(new MemberDepartmentNameDTO(vacation));
        }
        return mdList;
    }

    private List<MemberDepartmentNameDTO> memberListToMDList(List<Member> memberList) {
        List<MemberDepartmentNameDTO> mdList = new ArrayList<>();
        for (Member member : memberList) {
            mdList.add(new MemberDepartmentNameDTO(member));
        }
        return mdList;
    }

    //?????? ????????? ???????????? 1, 2??? ????????? ????????? ?????? ????????? ???????????? ?????? ??? ??????????????? ??????????????? ??????
    private void rectifyToAttendance(RectifyAttendance rectifyAttendance) {

        if (rectifyAttendance.isApproval1() && rectifyAttendance.isApproval2() && !rectifyAttendance.isDeleted()) {
            Attendance attendance = dtoUtil.RectifyToAttendance(rectifyAttendance);
            attendance.setAuthor(rectifyAttendance.getAuthor());
            Attendance a = attendanceRepository.save(attendance);

            rectifyAttendance.setDeleted(true);
            rectifyAttendanceRepository.save(rectifyAttendance);
        }
    }

    //????????? ?????? ????????????
    private Department getDepartment() {
        return Objects.requireNonNull(getMember()).getDepartment();
    }

    //?????? ??? ????????? ????????? 1, 2??? ?????? ?????? ??????
    private boolean rectifyApproved(RectifyAttendance rectifyAttendance) {
        String leaderId = getMemberId();
        //LVL2(?????????) ??? ?????? ??????????????? ????????? ??????????????? ?????? ??? ?????? ??????
        if (authorityUtil.checkLevel() == 2) {
            Department department_m = rectifyAttendance.getAuthor().getDepartment();
            Department department_l = departmentRepository.findByLeader_MemberIdAndDeletedFalse(leaderId);
            if (department_m.equals(department_l)) {
                setApprovalAndApprover(rectifyAttendance, 1);
                return true;
            }
        }
        //LVL3(??????)????????? ?????? ?????? ??????
        else if (3 <= authorityUtil.checkLevel()) {
            setApprovalAndApprover(rectifyAttendance, 2);
            return true;
        }
        return false;
    }

    //????????? ????????? ??????
    private void setApprovalAndApprover(RectifyAttendance rectifyAttendance, int degree) {
        Member member = getMember();
        if (degree == 1) {
            rectifyAttendance.setApproval1(true);
            rectifyAttendance.setApprover1(getMember());
        } else if (degree == 2) {
            if (!rectifyAttendance.isApproval1()) {
                rectifyAttendance.setApproval1(true);
                rectifyAttendance.setApprover1(member);
            }
            rectifyAttendance.setApproval2(true);
            rectifyAttendance.setApprover2(member);
        }
    }

    //?????? ?????? ?????? ?????? ????????? ????????????
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


