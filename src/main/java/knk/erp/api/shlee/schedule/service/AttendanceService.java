package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.DepartmentRepository;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.CommonUtil;
import knk.erp.api.shlee.schedule.dto.Attendance.*;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.repository.AttendanceRepository;
import knk.erp.api.shlee.schedule.repository.RectifyAttendanceRepository;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.schedule.specification.AS;
import knk.erp.api.shlee.schedule.specification.RAS;
import knk.erp.api.shlee.schedule.specification.SS;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final RectifyAttendanceRepository rectifyAttendanceRepository;
    private final AttendanceUtil util;
    private final CommonUtil commonUtil;

    /**
     * 권한 여부 체크를 위한 사용자, 부서 리포지토리 접근
     **/
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    //테스트
    public void test(){
        //일 0 토 6
        int[] monthStart = {31, 28, 31, 30, 31};
        Member member = getMember();
        for(int i=0; i<5; i++){
            int year = 2021;
            int month = i+1;
            for(int j=0; j<monthStart[i]; j++){
                int day = j+1;
                LocalDate date = LocalDate.of(year, month, day);
                if(date.getDayOfWeek().getValue() < 6){
                    LocalTime onWork = LocalTime.of(9,0);
                    LocalTime offWork = LocalTime.of(18,0);
                    Attendance attendance = Attendance.builder().attendanceDate(date).onWork(onWork).offWork(offWork).build();
                    attendance.setAuthor(member);
                    attendanceRepository.save(attendance);
                }

            }
        }


    }

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

            List<Attendance> attendanceList = attendanceRepository.findAll(AS.delFalse().and(AS.mid(memberId)).and(AS.attendanceDateBetween(startDate,endDate)));
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

            if(!attendanceOptional.isPresent()){
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
                rectifyAttendanceList = rectifyAttendanceRepository.findAll(RAS.delFalse().and(RAS.did(departmentId)));
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
            String memberId = getMemberId();
            int onWork;//출근
            int yetWork;//미출근
            int lateWork;//지각
            LocalDate today = LocalDate.now();
            LocalTime nine = LocalTime.of(9, 0, 0);

            if (commonUtil.checkLevel() == 2) {
                Long departmentId = getDepartmentId(memberId);
                Department department = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId).getDepartment();
                onWork = (int) attendanceRepository.count(AS.delFalse().and(AS.atteDate(today).and(AS.did(departmentId))));
                lateWork = (int) attendanceRepository.count(AS.delFalse().and(AS.atteDate(today).and(AS.did(departmentId).and(AS.onWorkAfter(nine)))));
                yetWork = department.getMemberList().size() - onWork;
            }
            else if (3 <= commonUtil.checkLevel()) {
                onWork = (int) attendanceRepository.count(AS.delFalse().and(AS.atteDate(today)));
                lateWork = (int) attendanceRepository.count(AS.delFalse().and(AS.atteDate(today).and(AS.onWorkAfter(nine))));
                yetWork = (int) memberRepository.count() - onWork;
            } else {
                return new ResponseCMD("RAS003");
            }
            return new ResponseCMD("RAS001", new AttendanceSummaryDTO(onWork, yetWork, lateWork));
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

    //정정 요청이 들어온뒤 1, 2차 승인이 완료된 경우 출퇴근 정정요청 삭제 후 해당정보로 출퇴근정보 생성
    private void rectifyToAttendance(Long id) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(id);

        if (rectifyAttendance.isApproval1() && rectifyAttendance.isApproval2() && !rectifyAttendance.isDeleted()) {
            Attendance attendance = util.RectifyToAttendance(rectifyAttendance);
            attendanceRepository.save(attendance);

            rectifyAttendance.setDeleted(true);
            rectifyAttendanceRepository.save(rectifyAttendance);
        }
    }

    //맴버 아이디로 부서 아이디 가져오기
    private Long getDepartmentId(String memberId) {
        try {
            return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId).getDepartment().getId();
        } catch (Exception e) {
            return -1L;
        }
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

    private Member getMember(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
        } catch (Exception e) {
            return null;
        }
    }

}


