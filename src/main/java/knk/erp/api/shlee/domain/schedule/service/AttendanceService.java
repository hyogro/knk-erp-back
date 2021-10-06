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
import knk.erp.api.shlee.exception.exceptions.schedule.AttendanceExistException;
import knk.erp.api.shlee.exception.exceptions.schedule.AttendanceNotExistException;
import knk.erp.api.shlee.exception.exceptions.schedule.AttendanceOffWorkExistException;
import knk.erp.api.shlee.exception.exceptions.schedule.RectifyAttendanceExistException;
import knk.erp.api.shlee.exception.exceptions.common.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
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
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final RectifyAttendanceRepository rectifyAttendanceRepository;
    private final VacationRepository vacationRepository;
    private final AttendanceUtil dtoUtil;
    private final AuthorityUtil authorityUtil;

    /**
     * 권한 여부 체크를 위한 사용자, 부서 리포지토리 접근
     **/
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    //출근 기록
    @Transactional
    public void onWork(String uuid) {
        //기존 출근내역이 있으면 실패
        throwIfTodayAttendanceExist();

        //기존 정정요청이 있으면 실패
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

    //퇴근 기록
    @Transactional
    public void offWork() {
        //근대기록을 조회하며, 존재하지 않을 시 실패
        Attendance attendance = throwIfAttendanceNotExistOrReturn();

        //기존 퇴근내역이 있으면 실패
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

    //출, 퇴근기록 조회
    @Transactional
    public List<AttendanceDto> readAttendanceList(LocalDate startDate, LocalDate endDate) {
        String memberId = EntityUtil.getInstance().getMemberId();
        List<Attendance> attendanceList = attendanceRepository.findAll(AS.searchWithDateBetween(memberId, startDate, endDate));
        return attendanceList.stream().map(AttendanceDto::new).collect(Collectors.toList());

    }

    //개인 출,퇴근 상세 조회
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

    //출,퇴근기록 정정 요청 -> 정정요청 신규 생성
    @Transactional
    public void createRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO) {
        String memberId = EntityUtil.getInstance().getMemberId();
        LocalDate attendanceDate = rectifyAttendanceDTO.getAttendanceDate();

        //요청일에 출퇴근정보 존재하면 예외처리(다른방식으로 접근해야 함)
        throwIfAttendanceExist(attendanceDate);

        //요청일에 정정요청 이미 존재하면 예외처리
        throwIfRectifyAttendanceExist(attendanceDate, memberId);

        //성공 - 생성 후 응답
        RectifyAttendance rectifyAttendance = rectifyAttendanceDTO.toEntity();
        System.out.println(rectifyAttendance.getAttendanceDate());
        rectifyAttendance.setAuthor(EntityUtil.getInstance().getMember(memberRepository));

        //TODO: 바꿔야함
        //레벨에 따른 1,2차 승인여부 변경
        rectifyApproved(rectifyAttendance);
        RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);

        //TODO: 바꿔야함
        //1,2차 승인시 출,퇴근 정보 등록
        rectifyToAttendance(done);
    }

    private Attendance throwIfAttendanceNotExist(Long attendanceId) {
        Optional<Attendance> attendanceOptional = attendanceRepository.findOne(AS.searchWithAttendanceId(attendanceId));
        if (!attendanceOptional.isPresent()) {
            throw new AttendanceNotExistException();
        }
        return attendanceOptional.get();
    }

    //출,퇴근기록 정정 요청 -> 출퇴근 기록으로 정정요청 생성
    @Transactional
    public void updateRectifyAttendance(Long aid, RectifyAttendanceDTO rectifyAttendanceDTO) {


        //기존 근태정보가 존재하지 않으면 예외처리
        Attendance attendance = throwIfAttendanceNotExist(aid);
        attendance.setDeleted(true);
        attendanceRepository.save(attendance);

        //요청일에 정정요청 이미 존재하면 예외처리
        String memberId = EntityUtil.getInstance().getMemberId();
        LocalDate attendanceDate = attendance.getAttendanceDate();
        System.out.println(attendanceDate);
        throwIfRectifyAttendanceExist(attendanceDate, memberId);

        //성공 - 생성 후 응답
        RectifyAttendance rectifyAttendance = rectifyAttendanceDTO.toEntity();
        rectifyAttendance.setAttendanceDate(attendance.getAttendanceDate());
        rectifyAttendance.setTargetId(aid);
        rectifyAttendance.setAuthor(EntityUtil.getInstance().getMember(memberRepository));

        //레벨에 따른 1,2차 승인여부 변경
        rectifyApproved(rectifyAttendance);
        RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);


        //1,2차 승인시 출,퇴근 정보 등록
        rectifyToAttendance(done);
    }

    //출,퇴근 정정요청목록 조회
    @Transactional
    public List<Object> readRectifyAttendanceList() {
        String memberId = EntityUtil.getInstance().getMemberId();

        List<RectifyAttendance> rectifyAttendanceList = rectifyAttendanceRepository.findAll(RAS.searchWithMemberId(memberId));
        return dtoUtil.entityToDto(rectifyAttendanceList);
    }

    //출,퇴근 정정요청상세 조회
    @Transactional
    public RectifyAttendanceDetailData readRectifyAttendance(Long rid) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);
        return new RectifyAttendanceDetailData(rectifyAttendance);
    }

    //출,퇴근 정정요청 삭제
    @Transactional
    public void deleteRectifyAttendance(Long rid) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);
        rectifyAttendance.setDeleted(true);
        rectifyAttendanceRepository.save(rectifyAttendance);
    }

    //승인해야할 출,퇴근 정정요청목록 조회
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

    //출,퇴근 정정 승인 레벨 2, 레벨 3만 접근 가능.
    @Transactional
    public void approveRectifyAttendance(Long rid) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);
        //TODO: 삭제되었을경우 예외처리
        //TODO: rectifyApproved(rectifyAttendance) 이용해서 승인처리하고 변경점 없을시 예외처리
        rectifyApproved(rectifyAttendance);

        RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);
        rectifyToAttendance(done);
    }

    //출,퇴근 요약정보 조회
    @Transactional
    public AttendanceSummaryDTO readAttendanceSummary() {
        LocalDate today = LocalDate.now();
        LocalDateTime st = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime et = LocalDateTime.of(today, LocalTime.MAX);

        if (authorityUtil.checkLevel() == 2) {
            Department department = getDepartment();
            Long departmentId = department.getId();

            List<Attendance> onWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today).and(AS.did(departmentId))));
            List<Attendance> offWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today).and(AS.offWorked()).and(AS.did(departmentId))));
            List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.did(departmentId)).and(VS.vacationDateBetween(st, et)).and(VS.approve1Is(true)).and(VS.approve2Is(true)));
            List<Attendance> lateWorkList = new ArrayList<>(onWorkList);
            List<Member> yetWorkList = department.getMemberList();

            return makeAttendanceSummary(onWorkList, offWorkList, vacationList, lateWorkList, yetWorkList);

        } else if (3 <= authorityUtil.checkLevel()) {
            List<Attendance> onWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today)));
            List<Attendance> offWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today).and(AS.offWorked())));
            List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.vacationDateBetween(st, et)).and(VS.approve1Is(true)).and(VS.approve2Is(true)));
            List<Attendance> lateWorkList = new ArrayList<>(onWorkList);
            List<Member> yetWorkList = memberRepository.findAll();

            return makeAttendanceSummary(onWorkList, offWorkList, vacationList, lateWorkList, yetWorkList);

        } else {
            throw new PermissionDeniedException();
        }
    }

    //개인 출,퇴근 당일정보 조회
    @Transactional
    public AttendanceDto readAttendanceToday() {
        String memberId = EntityUtil.getInstance().getMemberId();
        LocalDate today = LocalDate.now();
        Optional<Attendance> attendance = attendanceRepository.findOne(AS.delFalse().and(AS.atteDate(today).and(AS.mid(memberId))));
        return attendance.map(AttendanceDto::new).orElseThrow(EntityNotFoundException::new);

    }

    //uuid 중복되는 출, 퇴근기록 조회
    @Transactional
    public List<Object> readDuplicateAttendanceList(LocalDate date) {
        List<Attendance> attendanceList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(date)));
        //List<String> uuidList = attendanceList.stream().map(Attendance::getUuid).collect(Collectors.toList());
        return dtoUtil.AttendanceUuidListToDTO(attendanceList);
    }


    //맴버의 출근 여부 확인
    private boolean checkAttendance(Member member, List<Attendance> onWorkList) {
        for (Attendance attendance : onWorkList) {
            if (attendance.getAuthor().equals(member)) return true;
        }
        return false;
    }

    //지각 맴버의 휴가 여부 확인
    private boolean checkVacationLate(Attendance attendance, List<Vacation> vacationList) {
        LocalDateTime onWorkTime = LocalDateTime.of(LocalDate.now(), attendance.getOnWork());
        for (Vacation vacation : vacationList) {
            if (vacation.getAuthor().equals(attendance.getAuthor())) {
                return vacation.getEndDate().isAfter(onWorkTime);
            }
        }
        return false;
    }

    //결근 맴버의 휴가 여부 확인
    private boolean checkVacation(Member member, List<Vacation> vacationList) {
        LocalDateTime now = LocalDateTime.now();
        for (Vacation vacation : vacationList) {
            if (vacation.getAuthor().equals(member)) {
                return vacation.getStartDate().isBefore(now) && vacation.getEndDate().isAfter(now);
            }
        }
        return false;
    }

    //요약 DTO 생성
    private AttendanceSummaryDTO makeAttendanceSummary(List<Attendance> onWorkList, List<Attendance> offWorkList, List<Vacation> vacationList, List<Attendance> lateWorkList, List<Member> yetWorkList) {

        List<MemberDepartmentNameDTO> onWork, offWork, yetWork, lateWork, vacation;//출근, 미출근, 지각, 휴가
        LocalTime nine = LocalTime.of(9, 0, 0);

        lateWorkList.removeIf(a -> a.getOnWork().isBefore(nine));// 9시 이전 출근자 삭제 = 지각 인원
        lateWorkList.removeIf(a -> checkVacationLate(a, vacationList));// 휴가자 삭제 = 지각 인원

        yetWorkList.removeIf(member -> checkAttendance(member, onWorkList)); //출근자 삭제 = 미출근 인원
        yetWorkList.removeIf(member -> checkVacation(member, vacationList)); // 휴가자 삭제 = 미출근 인원

        onWork = attendanceListToMDList(onWorkList);
        offWork = attendanceListToMDList(offWorkList);
        lateWork = attendanceListToMDList(lateWorkList);
        vacation = vacationListToMDList(vacationList);
        yetWork = memberListToMDList(yetWorkList);

        onWork.removeIf(i -> i.getMemberName().equals("관리자"));
        offWork.removeIf(i -> i.getMemberName().equals("관리자"));
        lateWork.removeIf(i -> i.getMemberName().equals("관리자"));
        vacation.removeIf(i -> i.getMemberName().equals("관리자"));
        yetWork.removeIf(i -> i.getMemberName().equals("관리자"));

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

    //정정 요청이 들어온뒤 1, 2차 승인이 완료된 경우 출퇴근 정정요청 삭제 후 해당정보로 출퇴근정보 생성
    private void rectifyToAttendance(RectifyAttendance rectifyAttendance) {

        if (rectifyAttendance.isApproval1() && rectifyAttendance.isApproval2() && !rectifyAttendance.isDeleted()) {
            Attendance attendance = dtoUtil.RectifyToAttendance(rectifyAttendance);
            attendance.setAuthor(rectifyAttendance.getAuthor());
            Attendance a = attendanceRepository.save(attendance);

            rectifyAttendance.setDeleted(true);
            rectifyAttendanceRepository.save(rectifyAttendance);
        }
    }

    //맴버로 부서 가져오기
    private Department getDepartment() {
        return Objects.requireNonNull(getMember()).getDepartment();
    }

    //요청 시 권한에 따라서 1, 2차 승인 여부 변경
    private boolean rectifyApproved(RectifyAttendance rectifyAttendance) {
        String leaderId = getMemberId();
        //LVL2(부서장) 인 경우 승인하려는 맴버가 부서원인지 확인 후 승인 진행
        if (authorityUtil.checkLevel() == 2) {
            Department department_m = rectifyAttendance.getAuthor().getDepartment();
            Department department_l = departmentRepository.findByLeader_MemberIdAndDeletedFalse(leaderId);
            if (department_m.equals(department_l)) {
                setApprovalAndApprover(rectifyAttendance, 1);
                return true;
            }
        }
        //LVL3(부장)이상인 경우 모두 승인
        else if (3 <= authorityUtil.checkLevel()) {
            setApprovalAndApprover(rectifyAttendance, 2);
            return true;
        }
        return false;
    }

    //승인및 승인자 설정
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


