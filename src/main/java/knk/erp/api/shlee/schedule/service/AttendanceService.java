package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.DepartmentRepository;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.CommonUtil;
import knk.erp.api.shlee.schedule.dto.Attendance.*;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Vacation;
import knk.erp.api.shlee.schedule.repository.AttendanceRepository;
import knk.erp.api.shlee.schedule.repository.RectifyAttendanceRepository;
import knk.erp.api.shlee.schedule.repository.VacationRepository;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.schedule.specification.AS;
import knk.erp.api.shlee.schedule.specification.RAS;
import knk.erp.api.shlee.schedule.specification.VS;
import knk.erp.api.shlee.schedule.util.AttendanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final RectifyAttendanceRepository rectifyAttendanceRepository;
    private final VacationRepository vacationRepository;
    private final AttendanceUtil util;
    private final CommonUtil commonUtil;

    /**
     * 권한 여부 체크를 위한 사용자, 부서 리포지토리 접근
     **/
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    //출근 기록
    public ResponseCM onWork() {
        try {
            String memberId = getMemberId();
            LocalDate today = LocalDate.now();
            LocalTime onWorkTime = LocalTime.now();

            //실패 - 기존 출근기록 있으면 리턴
            boolean isOnWorked = (int) attendanceRepository.count(AS.delFalse().and(AS.mid(memberId)).and(AS.atteDate(today))) != 0;
            if (isOnWorked) return new ResponseCM("ON003");

            AttendanceDTO attendanceDTO = new AttendanceDTO(today, onWorkTime);
            Attendance attendance = attendanceDTO.toEntity();
            attendance.setAuthor(getMember());
            attendanceRepository.save(attendance);
            return new ResponseCM("ON001");

        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCM("ON002", e.getMessage());
        }
    }

    //퇴근 기록
    public ResponseCM offWork() {
        try {
            String memberId = getMemberId();
            LocalDate today = LocalDate.now();
            LocalTime offWorkTime = LocalTime.now();

            Optional<Attendance> attendanceOptional = attendanceRepository.findOne(AS.delFalse().and(AS.mid(memberId)).and(AS.atteDate(today)));
            if (!attendanceOptional.isPresent()) return new ResponseCM("OFF004");

            //실패 - 기존 퇴근기록 있으면 수정불가
            Attendance attendance = attendanceOptional.get();
            if (attendance.getOffWork() != null) return new ResponseCM("OFF003");

            //성공 - 기존 퇴근기록 없으면 생성 후 응답
            attendance.setOffWork(offWorkTime);
            attendanceRepository.save(attendance);
            return new ResponseCM("OFF001");
        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCM("OFF002", e.getMessage());
        }
    }

    //출, 퇴근기록 조회
    public ResponseCMDL readAttendanceList(LocalDate startDate, LocalDate endDate) {
        try {
            String memberId = getMemberId();

            List<Attendance> attendanceList = attendanceRepository.findAll(AS.delFalse().and(AS.mid(memberId)).and(AS.attendanceDateBetween(startDate, endDate)));
            return new ResponseCMDL("RAL001", util.AttendanceListToDTO(attendanceList));
        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCMDL("RAL002", e.getMessage());
        }
    }

    //개인 출,퇴근 상세 조회
    public ResponseCMD readAttendance(Long aid) {
        try {
            Attendance attendance = attendanceRepository.getOne(aid);
            return new ResponseCMD("RAD001", new AttendanceDetailData(attendance));
        } catch (Exception e) {
            return new ResponseCMD("RAD002", e.getMessage());
        }
    }

    //출,퇴근기록 정정 요청 -> 정정요청 신규 생성
    public ResponseCM createRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO) {
        try {
            //성공 - 생성 후 응답
            RectifyAttendance rectifyAttendance = rectifyAttendanceDTO.toEntity();
            rectifyAttendance.setAuthor(getMember());

            //레벨에 따른 1,2차 승인여부 변경
            rectifyApproved(rectifyAttendance);
            RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);

            //1,2차 승인시 출,퇴근 정보 등록
            rectifyToAttendance(done.getId());

            return new ResponseCM("CRA001");
        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCM("CRA002", e.getMessage());
        }
    }

    //출,퇴근기록 정정 요청 -> 출퇴근 기록으로 정정요청 생성
    public ResponseCM updateRectifyAttendance(Long aid, RectifyAttendanceDTO rectifyAttendanceDTO) {
        try {
            Optional<Attendance> attendanceOptional = attendanceRepository.findOne(AS.delFalse().and(AS.id(aid)));

            if (!attendanceOptional.isPresent()) {
                return new ResponseCM("URA002");
            }

            Attendance attendance = attendanceOptional.get();

            RectifyAttendance rectifyAttendance = util.AttendanceToRectify(attendance, rectifyAttendanceDTO);
            rectifyAttendance.setAuthor(getMember());
            //레벨에 따른 1,2차 승인여부 변경
            rectifyApproved(rectifyAttendance);
            RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);

            attendance.setDeleted(true);
            attendanceRepository.save(attendance);

            //1,2차 승인시 출,퇴근 정보 등록
            rectifyToAttendance(done.getId());

            return new ResponseCM("URA001");
        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCM("URA002", e.getMessage());
        }
    }

    //출,퇴근 정정요청목록 조회
    public ResponseCMDL readRectifyAttendanceList() {
        try {
            String memberId = getMemberId();

            List<RectifyAttendance> rectifyAttendanceList = rectifyAttendanceRepository.findAll(RAS.delFalse().and(RAS.mid(memberId)));
            return new ResponseCMDL("RRAL001", util.RectifyAttendanceListToDTO(rectifyAttendanceList));
        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCMDL("RRAL002", e.getMessage());
        }
    }

    //출,퇴근 정정요청상세 조회
    public ResponseCMD readRectifyAttendance(Long rid) {
        try {
            RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);
            return new ResponseCMD("RRA001", new RectifyAttendanceDetailData(rectifyAttendance));
        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCMD("RRA002", e.getMessage());
        }
    }

    //출,퇴근 정정요청 삭제
    public ResponseCM deleteRectifyAttendance(Long rid) {
        try {
            RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);

            rectifyAttendance.setDeleted(true);
            rectifyAttendanceRepository.save(rectifyAttendance);
            return new ResponseCM("DRA001");
        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCM("DRA002", e.getMessage());
        }
    }

    //승인해야할 출,퇴근 정정요청목록 조회
    public ResponseCMDL readRectifyAttendanceListForApprove() {
        try {
            String memberId = getMemberId();
            List<RectifyAttendance> rectifyAttendanceList = new ArrayList<>();
            if (commonUtil.checkLevel() == 2) {
                Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                Long departmentId = member.getDepartment().getId();
                rectifyAttendanceList = rectifyAttendanceRepository.findAll(RAS.delFalse().and(RAS.did(departmentId).and(RAS.approve1Is(false))));
            } else if (3 <= commonUtil.checkLevel()) {
                rectifyAttendanceList = rectifyAttendanceRepository.findAll(RAS.delFalse());
            }
            return new ResponseCMDL("RRAL001", util.RectifyAttendanceListToDTO(rectifyAttendanceList));
        } catch (Exception e) {
            //실패 - Exception 발생
            return new ResponseCMDL("RRAL002", e.getMessage());
        }
    }

    //출,퇴근 정정 승인 레벨 2, 레벨 3만 접근 가능.
    public ResponseCM approveRectifyAttendance(Long rid) {
        try {
            RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rid);
            if (rectifyAttendance.isDeleted()) return new ResponseCM("ARA004");
            if (!rectifyApproved(rectifyAttendance)) return new ResponseCM("ARA003");

            RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);
            rectifyToAttendance(done.getId());
            return new ResponseCM("ARA001");
        } catch (Exception e) {
            return new ResponseCM("ARA002", e.getMessage());
        }
    }

    //출,퇴근 요약정보 조회
    public ResponseCMD readAttendanceSummary() {
        try {

            LocalDate today = LocalDate.now();
            LocalDateTime now = LocalDateTime.now();

            if (commonUtil.checkLevel() == 2) {
                Department department = getDepartment();
                Long departmentId = department.getId();

                List<Attendance> onWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today).and(AS.did(departmentId))));
                List<Attendance> offWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today).and(AS.offWorked()).and(AS.did(departmentId))));
                List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.did(departmentId)).and(VS.targetDateBetween(now)).and(VS.approve1Is(true)).and(VS.approve2Is(true)));
                List<Attendance> lateWorkList = new ArrayList<>(onWorkList);
                List<Member> yetWorkList = department.getMemberList();

                return new ResponseCMD("RAS001", makeAttendanceSummary(onWorkList, offWorkList, vacationList, lateWorkList, yetWorkList));

            } else if (3 <= commonUtil.checkLevel()) {
                List<Attendance> onWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today)));
                List<Attendance> offWorkList = attendanceRepository.findAll(AS.delFalse().and(AS.atteDate(today).and(AS.offWorked())));
                List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.targetDateBetween(now)).and(VS.approve1Is(true)).and(VS.approve2Is(true)));
                List<Attendance> lateWorkList = new ArrayList<>(onWorkList);
                List<Member> yetWorkList = memberRepository.findAll();

                return new ResponseCMD("RAS001", makeAttendanceSummary(onWorkList, offWorkList, vacationList, lateWorkList, yetWorkList));

            } else {
                return new ResponseCMD("RAS003");
            }
        } catch (Exception e) {
            return new ResponseCMD("RAS002", e.getMessage());
        }
    }

    //개인 출,퇴근 당일정보 조회
    public ResponseCMD readAttendanceToday() {
        try {
            String memberId = getMemberId();
            LocalDate today = LocalDate.now();
            Optional<Attendance> attendance = attendanceRepository.findOne(AS.delFalse().and(AS.atteDate(today).and(AS.mid(memberId))));
            return attendance.map(value -> new ResponseCMD("RA001", new AttendanceDTO(value))).orElseGet(() -> new ResponseCMD("RA003"));
        } catch (Exception e) {
            return new ResponseCMD("RA002", e.getMessage());
        }
    }

    //맴버의 출근 여부 확인
    private boolean checkAttendance(Member member, List<Attendance> onWorkList) {
        for (Attendance attendance : onWorkList) {
            if (attendance.getAuthor().equals(member)) return true;
        }
        return false;
    }

    //맴버의 휴가 여부 확인
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
        lateWorkList.removeIf(a -> checkVacation(a.getAuthor(), vacationList));// 휴가자 삭제 = 지각 인원

        yetWorkList.removeIf(member -> checkAttendance(member, onWorkList)); //출근자 삭제 = 미출근 인원
        yetWorkList.removeIf(member -> checkVacation(member, vacationList)); // 휴가자 삭제 = 미출근 인원

        onWork = attendanceListToMDList(onWorkList);
        offWork = attendanceListToMDList(offWorkList);
        lateWork = attendanceListToMDList(lateWorkList);
        vacation = vacationListToMDList(vacationList);
        yetWork = memberListToMDList(yetWorkList);

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
    private void rectifyToAttendance(Long id) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(id);

        if (rectifyAttendance.isApproval1() && rectifyAttendance.isApproval2() && !rectifyAttendance.isDeleted()) {
            Attendance attendance = util.RectifyToAttendance(rectifyAttendance);
            attendance.setAuthor(rectifyAttendance.getAuthor());
            System.out.println("여기부터 들어오긴 왔다");
            Attendance a = attendanceRepository.save(attendance);
            System.out.println(a.getAuthor().getMemberId());
            System.out.println("여기까지 들어오긴 왔다");

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
        if (commonUtil.checkLevel() == 2) {
            Department department_m = rectifyAttendance.getAuthor().getDepartment();
            Department department_l = departmentRepository.findByLeader_MemberIdAndDeletedFalse(leaderId);
            if (department_m.equals(department_l)) {
                setApprovalAndApprover(rectifyAttendance, 1);
                return true;
            }
        }
        //LVL3(부장)이상인 경우 모두 승인
        else if (3 <= commonUtil.checkLevel()) {
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


