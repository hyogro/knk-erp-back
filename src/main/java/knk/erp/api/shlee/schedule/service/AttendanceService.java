package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.*;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import knk.erp.api.shlee.common.util.CommonUtil;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AttendanceRepository attendanceRepository;
    private final RectifyAttendanceRepository rectifyAttendanceRepository;
    private final TokenProvider tokenProvider;
    private final AttendanceUtil util;
    private final CommonUtil commonUtil;

    /**
     * 권한 여부 체크를 위한 사용자, 부서 리포지토리 접근
     **/
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * 권한 여부 체크를 위한 사용자, 부서 리포지토리 접근
     **/

    //출근 기록
    public RES_onWork onWork(AttendanceDTO attendanceDTO, String token) {
        try {
            String memberId = attendanceDTO.getMemberId();
            LocalDateTime now = LocalDateTime.now();

            //실패 - 기존 출근기록 있으면 리턴
            boolean isOnWorked = attendanceRepository.countByAttendanceDateAndMemberIdAndDeletedIsFalse(now.toLocalDate(), memberId) != 0;
            if (isOnWorked) return new RES_onWork("ON003");

            //실패 - 본인이 아니면 생성불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(memberId);
            if (!isOwner) return new RES_onWork("ON004");

            //성공 - 기존 출근기록 없으면 생성 후 응답
            attendanceDTO.setAttendanceDate(now.toLocalDate());
            attendanceDTO.setOnWork(now);
            attendanceDTO.setDepartmentId(getDepartmentIdByMemberId(memberId));
            attendanceRepository.save(attendanceDTO.toEntity());
            return new RES_onWork("ON001");

        } catch (Exception e) {
            //실패 - Exception 발생
            return new RES_onWork("ON002", e.getMessage());
        }
    }

    //퇴근 기록
    public RES_offWork offWork(AttendanceDTO attendanceDTO, String token) {
        try {
            LocalDateTime now = LocalDateTime.now();

            //실패 - 기존 퇴근기록 있으면 수정불가
            Attendance attendance = attendanceRepository.getOne(attendanceDTO.getId());
            if (attendance.getOffWork() != null) return new RES_offWork("OFF003");

            //실패 - 본인이 아니면 생성불가
            boolean isOwner = tokenProvider.getAuthentication(token).getName().equals(attendance.getMemberId());
            if (!isOwner) return new RES_offWork("OFF004");

            //성공 - 기존 퇴근기록 없으면 생성 후 응답
            attendance.setOffWork(now);
            attendanceRepository.save(attendance);
            return new RES_offWork("OFF001");
        } catch (Exception e) {
            //실패 - Exception 발생
            return new RES_offWork("OFF002", e.getMessage());
        }
    }

    //출, 퇴근기록 조회
    public RES_readAttendanceList readAttendanceList(String token) {
        try {
            Authentication authentication = tokenProvider.getAuthentication(token);
            String memberId = authentication.getName();

            //성공 - 삭제되지 않은 기록 중 본인의 것만 조회
            List<Attendance> attendanceList = attendanceRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
            return new RES_readAttendanceList("RAL001", util.AttendanceListToDTO(attendanceList));
        } catch (Exception e) {
            //실패 - Exception 발생
            return new RES_readAttendanceList("RAL002", e.getMessage());
        }
    }

    //출,퇴근기록 정정 요청 -> 정정요청 신규 생성
    public RES_createRectifyAttendance createRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO, String token) {
        try {
            Authentication authentication = tokenProvider.getAuthentication(token);
            String memberId = authentication.getName();

            rectifyAttendanceDTO.setMemberId(memberId);
            rectifyAttendanceDTO.setDepartmentId(getDepartmentIdByMemberId(memberId));

            //성공 - 생성 후 응답
            RectifyAttendance rectifyAttendance = rectifyAttendanceDTO.toEntity();

            //레벨에 따른 1,2차 승인여부 변경
            rectifyApproved(authentication, rectifyAttendance);
            RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);

            //1,2차 승인시 출,퇴근 정보 등록
            rectifyToAttendance(done.getId());

            return new RES_createRectifyAttendance("CRA001");
        } catch (Exception e) {
            //실패 - Exception 발생
            return new RES_createRectifyAttendance("CRA002", e.getMessage());
        }
    }

    //출,퇴근기록 정정 요청 -> 출퇴근 기록으로 정정요청 생성
    public RES_updateRectifyAttendance updateRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO, String token) {
        try {
            Authentication authentication = tokenProvider.getAuthentication(token);
            String memberId = authentication.getName();

            Attendance attendance = attendanceRepository.getOne(rectifyAttendanceDTO.getId());

            //실패 - 본인이 아니면 수정불가
            if (!attendance.getMemberId().equals(memberId)) return new RES_updateRectifyAttendance("URA003");
            //실패 - 삭제된경우 수정 불가
            if (attendance.isDeleted()) return new RES_updateRectifyAttendance("URA004");

            RectifyAttendance rectifyAttendance = util.AttendanceToRectify(attendance, rectifyAttendanceDTO);
            rectifyAttendance.setDepartmentId(getDepartmentIdByMemberId(memberId));

            //레벨에 따른 1,2차 승인여부 변경
            rectifyApproved(authentication, rectifyAttendance);
            RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);

            attendance.setDeleted(true);
            attendanceRepository.save(attendance);

            //1,2차 승인시 출,퇴근 정보 등록
            rectifyToAttendance(done.getId());

            return new RES_updateRectifyAttendance("URA001");
        } catch (Exception e) {
            //실패 - Exception 발생
            return new RES_updateRectifyAttendance("URA002", e.getMessage());
        }
    }

    //정정 요청이 들어온뒤 1, 2차 승인이 완료된 경우 출퇴근 정정요청 삭제 후 해당정보로 출퇴근정보 생성
    private void rectifyToAttendance(Long id) {
        RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(id);

        if (rectifyAttendance.isApproval_1() && rectifyAttendance.isApproval_2()) {
            Attendance attendance = util.RectifyToAttendance(rectifyAttendance);

            attendanceRepository.save(attendance);

            rectifyAttendance.setDeleted(true);
            rectifyAttendanceRepository.save(rectifyAttendance);
        }
    }

    //맴버 아이디로 부서 아이디 가져오기
    private Long getDepartmentIdByMemberId(String memberId) {
        return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId).getDepartment().getId();
    }

    //인증 정보를 통해 권한 텍스트 출력
    public String getRoleByAuthentication(Authentication authentication) {
        return authentication.getAuthorities().toString().replace("[ROLE_", "").replace("]", "");
    }

    //요청 시 권한에 따라서 1, 2차 승인 여부 변경
    private boolean rectifyApproved(Authentication authentication, RectifyAttendance rectifyAttendance) {
        String lvl = getRoleByAuthentication(authentication);
        String leaderId = authentication.getName();
        String memberId = rectifyAttendance.getMemberId();

        //LVL2(부서장) 인 경우 승인하려는 맴버가 부서원인지 확인 후 승인 진행
        if (lvl.equals("LVL2")) {
            Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
            Department department_l = departmentRepository.findByLeader_MemberId(leaderId);
            if (member.getDepartment().getId().equals(department_l.getId())) {
                rectifyAttendance.setApproval_1(true);
                rectifyAttendance.setApprover_1(leaderId);
                return true;
            }
        }
        //LVL3(부장) 인 경우 모두 승인
        else if (lvl.equals("LVL3")) {
            rectifyAttendance.setApproval_1(true);
            rectifyAttendance.setApprover_1(leaderId);
            rectifyAttendance.setApproval_2(true);
            rectifyAttendance.setApprover_2(leaderId);
            return true;
        }
        return false;
    }

    //출,퇴근 정정요청목록 조회
    public RES_readRectifyAttendanceList readRectifyAttendanceList(RectifyAttendanceDTO rectifyAttendanceDTO, String token) {
        try {
            String memberId = tokenProvider.getAuthentication(token).getName();

            List<RectifyAttendance> rectifyAttendanceList = rectifyAttendanceRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
            return new RES_readRectifyAttendanceList("RRAL001", util.RectifyAttendanceListToDTO(rectifyAttendanceList));
        } catch (Exception e) {
            //실패 - Exception 발생
            return new RES_readRectifyAttendanceList("RRAL002", e.getMessage());
        }
    }

    //출,퇴근 정정요청 삭제
    public RES_deleteRectifyAttendance deleteRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO, String token) {
        try {
            String memberId = tokenProvider.getAuthentication(token).getName();

            RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rectifyAttendanceDTO.getId());

            if (!rectifyAttendance.getMemberId().equals(memberId)) return new RES_deleteRectifyAttendance("DRA003");

            rectifyAttendance.setDeleted(true);
            rectifyAttendanceRepository.save(rectifyAttendance);
            return new RES_deleteRectifyAttendance("DRA001");
        } catch (Exception e) {
            //실패 - Exception 발생
            return new RES_deleteRectifyAttendance("DRA002", e.getMessage());
        }
    }

    //출,퇴근 정정 승인 레벨 2, 레벨 3만 접근 가능.
    public RES_approveRectifyAttendance approveRectifyAttendance(RectifyAttendanceDTO rectifyAttendanceDTO, String token) {
        try {
            Authentication authentication = tokenProvider.getAuthentication(token);
            RectifyAttendance rectifyAttendance = rectifyAttendanceRepository.getOne(rectifyAttendanceDTO.getId());
            if (!rectifyApproved(authentication, rectifyAttendance)) return new RES_approveRectifyAttendance("ARA003");
            RectifyAttendance done = rectifyAttendanceRepository.save(rectifyAttendance);
            rectifyToAttendance(done.getId());
            return new RES_approveRectifyAttendance("ARA001");
        } catch (Exception e) {
            return new RES_approveRectifyAttendance("ARA002", e.getMessage());
        }
    }

    //출,퇴근 요약정보 조회
    public RES_readAttendanceSummary readAttendanceSummary(String token) {
        try {
            Authentication authentication = tokenProvider.getAuthentication(token);
            String lvl = getRoleByAuthentication(authentication);
            String memberId = authentication.getName();
            int onWork = 0;
            int yetWork = 0;
            int lateWork = 0;
            int vacation = 0;
            LocalTime nine = LocalTime.of(9, 0, 0);

            if (lvl.equals("LVL1")) {
                return new RES_readAttendanceSummary("RSS002", "");
            } else if (lvl.equals("LVL2")) {
                Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                Department department = member.getDepartment();
                int countOfMember = department.getMemberList().size();
                long departmentId = department.getId();
                LocalDate today = LocalDate.now();
                onWork = attendanceRepository.countByAttendanceDateAndDepartmentIdAndDeletedIsFalse(today, departmentId);
                lateWork = attendanceRepository.countByAttendanceDateAndDepartmentIdAndOnWorkAfterAndDeletedIsFalse(today, departmentId, LocalDateTime.of(today, nine));
                vacation = 0;
                yetWork = countOfMember - onWork - lateWork - vacation;
            } else if (lvl.equals("LVL3")) {
                

            }
            return new RES_readAttendanceSummary("RSS001", new AttendanceSummaryDTO(onWork, yetWork, lateWork, vacation));
        } catch (Exception e) {
            return new RES_readAttendanceSummary("RSS002", e.getMessage());
        }
    }


}


