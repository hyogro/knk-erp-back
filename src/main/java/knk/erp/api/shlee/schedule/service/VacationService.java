package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.CommonUtil;
import knk.erp.api.shlee.schedule.dto.Attendance.AttendanceSummaryDTO;
import knk.erp.api.shlee.schedule.dto.Attendance.RES_readAttendanceSummary;
import knk.erp.api.shlee.schedule.dto.Vacation.*;
import knk.erp.api.shlee.schedule.entity.Vacation;
import knk.erp.api.shlee.schedule.repository.VacationRepository;
import knk.erp.api.shlee.schedule.util.VacationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class VacationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VacationRepository vacationRepository;
    private final CommonUtil commonUtil;
    private final VacationUtil util;

    /**
     * 권한 여부 체크를 위한 사용자, 부서 리포지토리 접근
     **/
    private final MemberRepository memberRepository;

    //휴가 생성
    public RES_createVacation createVacation(VacationDTO vacationDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            vacationDTO.setMemberId(memberId);
            vacationDTO.setDepartmentId(getDepartmentIdByMemberId(memberId));

            Vacation vacation = vacationDTO.toEntity();
            approveCheck(authentication, vacation);

            vacationRepository.save(vacation);
            return new RES_createVacation("CV001");
        } catch (Exception e) {
            return new RES_createVacation("CV002", e.getMessage());
        }
    }

    //내 휴가목록 조회
    public RES_readVacationList readVacationList() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            List<Vacation> vacationList = vacationRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
            return new RES_readVacationList("RVL001", util.VacationListToDTO(vacationList));
        } catch (Exception e) {
            return new RES_readVacationList("RVL002", e.getMessage());
        }
    }

    //내 휴가 삭제
    public RES_deleteVacation deleteVacation(VacationDTO vacationDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();

            Vacation vacation = vacationRepository.getOne(vacationDTO.getId());
            if (vacation.isApproval1() && vacation.isApproval2()) return new RES_deleteVacation("DV003");
            if (vacation.getMemberId().equals(memberId)) return new RES_deleteVacation("DV004");

            vacation.setDeleted(true);
            vacationRepository.save(vacation);
            return new RES_deleteVacation("DV001");
        } catch (Exception e) {
            return new RES_deleteVacation("DV002", e.getMessage());
        }
    }

    //승인, 거부할 휴가목록 조회
    public RES_readVacationList readVacationListForApprove() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            List<Vacation> vacationList = new ArrayList<>();

            if (commonUtil.checkMaster(authentication) == 2) {
                Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                Long departmentId = member.getDepartment().getId();
                vacationList = vacationRepository.findAllByDepartmentIdAndApproval1IsFalseAndDeletedIsFalse(departmentId);

            } else if (3 <= commonUtil.checkMaster(authentication)) {
                vacationList = vacationRepository.findAllByApproval2IsFalseAndDeletedIsFalse();
            }
            return new RES_readVacationList("RVL001", util.VacationListToDTO(vacationList));
        } catch (Exception e) {
            return new RES_readVacationList("RVL002", e.getMessage());
        }
    }

    //휴가 승인
    public RES_approveVacation approveVacation(VacationDTO vacationDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            Vacation vacation = vacationRepository.getOne(vacationDTO.getId());

            if (commonUtil.checkMaster(authentication) == 2) {
                Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                Long departmentId = member.getDepartment().getId();
                if (!vacation.getDepartmentId().equals(departmentId)) return new RES_approveVacation("AV003");
            }

            boolean isChange = approveCheck(authentication, vacation);
            if(isChange) vacationRepository.save(vacation);

            return isChange ? new RES_approveVacation("AV001") : new RES_approveVacation("AV003");

        } catch (Exception e) {
            return new RES_approveVacation("AV002", e.getMessage());
        }
    }

    //휴가 거절
    public RES_rejectVacation rejectVacation(REQ_rejectVacation reject) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();

            if (2 <= commonUtil.checkMaster(authentication)) {

                Vacation vacation = vacationRepository.getOne(reject.getId());
                vacation.setReject(true);
                vacation.setRejectMemo(reject.getRejectMemo());

                if (vacation.isApproval1() && vacation.isApproval2()) return new RES_rejectVacation("RV004");

                if (commonUtil.checkMaster(authentication) == 2) {
                    Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                    Long departmentId = member.getDepartment().getId();
                    if (!vacation.getDepartmentId().equals(departmentId)) return new RES_rejectVacation("RV003");
                }

                vacationRepository.save(vacation);
                return new RES_rejectVacation("RV001");
            }
            return new RES_rejectVacation("RV003");

        } catch (Exception e) {
            return new RES_rejectVacation("RV002", e.getMessage());
        }
    }

    //휴가 요약정보 조회
    public RES_readVacationSummary readVacationSummary(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            int vacation; //휴가
            LocalDateTime todayS = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
            LocalDateTime todayE = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

            if (commonUtil.checkMaster(authentication) == 2) {
                Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                Department department = member.getDepartment();
                vacation = vacationRepository.countAllByDepartmentIdAndStartDateBeforeAndEndDateAfterAndDeletedIsFalse(department.getId(), todayS, todayE);

            } else if (3 <= commonUtil.checkMaster(authentication)) {
                vacation = vacationRepository.countAllByStartDateAfterAndEndDateBeforeAndDeletedIsFalse(todayS, todayE);
            } else {
                return new RES_readVacationSummary("RVS003");
            }
            return new RES_readVacationSummary("RVS001", new VacationSummaryDTO(vacation));
        } catch (Exception e) {
            return new RES_readVacationSummary("RVS002", e.getMessage());
        }
    }

    //권한 체크 및 승인여부 변경
    private boolean approveCheck(Authentication authentication, Vacation vacation) {
        if (commonUtil.checkMaster(authentication) == 2) {
            vacation.setApproval1(true);
            vacation.setApprover1(authentication.getName());
            return true;
        } else if (3 <= commonUtil.checkMaster(authentication)) {
            if (!vacation.isApproval1()) {
                vacation.setApproval1(true);
                vacation.setApprover1(authentication.getName());
            }
            vacation.setApproval2(true);
            vacation.setApprover2(authentication.getName());
            return true;
        }
        return false;
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


