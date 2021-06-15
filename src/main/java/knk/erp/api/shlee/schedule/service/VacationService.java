package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.CommonUtil;
import knk.erp.api.shlee.schedule.dto.Vacation.*;
import knk.erp.api.shlee.schedule.entity.Vacation;
import knk.erp.api.shlee.schedule.repository.VacationRepository;
import knk.erp.api.shlee.schedule.responseEntity.vacation.*;
import knk.erp.api.shlee.schedule.specification.VS;
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
            Member member = getMember();

            Vacation vacation = vacationDTO.toEntity();
            vacation.setAuthor(member);
            approveCheck(vacation);

            vacationRepository.save(vacation);
            return new RES_createVacation("CV001");
        } catch (Exception e) {
            return new RES_createVacation("CV002", e.getMessage());
        }
    }

    //내 휴가목록 조회
    public RES_readVacationList readVacationList() {
        try {
            String memberId = getMemberId();
            List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.mid(memberId)));
            return new RES_readVacationList("RVL001", util.VacationListToDTO(vacationList));
        } catch (Exception e) {
            return new RES_readVacationList("RVL002", e.getMessage());
        }
    }

    //내 휴가상세 조회
    public RES_readVacation readVacation(Long vid) {
        try {
            Vacation vacation = vacationRepository.getOne(vid);
            return new RES_readVacation("RV001", new VacationDTO(vacation));
        } catch (Exception e) {
            return new RES_readVacation("RV002", e.getMessage());
        }
    }

    //내 휴가 삭제
    public RES_deleteVacation deleteVacation(Long vid) {
        try {
            String memberId = getMemberId();

            Vacation vacation = vacationRepository.getOne(vid);
            if (vacation.isApproval1() && vacation.isApproval2()) return new RES_deleteVacation("DV003");
            if (vacation.getAuthor().getMemberId().equals(memberId)) return new RES_deleteVacation("DV004");

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
            String memberId = getMemberId();
            List<Vacation> vacationList = new ArrayList<>();

            if (commonUtil.checkLevel() == 2) {
                Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                Long departmentId = member.getDepartment().getId();
                vacationList = vacationRepository.findAll(VS.delFalse().and(VS.did(departmentId)).and(VS.approve1Is(false)));

            } else if (3 <= commonUtil.checkLevel()) {
                vacationList = vacationRepository.findAll(VS.delFalse().and(VS.approve2Is(false)));
            }
            return new RES_readVacationList("RVL001", util.VacationListToDTO(vacationList));
        } catch (Exception e) {
            return new RES_readVacationList("RVL002", e.getMessage());
        }
    }

    //휴가 승인
    public RES_approveVacation approveVacation(Long vid) {
        try {
            String memberId = getMemberId();
            Vacation vacation = vacationRepository.getOne(vid);

            if (commonUtil.checkLevel() == 2) {
                Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                Long departmentId = member.getDepartment().getId();
                if (!vacation.getAuthor().getDepartment().getId().equals(departmentId)) return new RES_approveVacation("AV003");
            }

            boolean isChange = approveCheck(vacation);
            if (isChange) vacationRepository.save(vacation);

            return isChange ? new RES_approveVacation("AV001") : new RES_approveVacation("AV003");

        } catch (Exception e) {
            return new RES_approveVacation("AV002", e.getMessage());
        }
    }

    //휴가 거절
    public RES_rejectVacation rejectVacation(Long vid, REQ_rejectVacation reject) {
        try {
            String memberId = getMemberId();

            if (2 <= commonUtil.checkLevel()) {

                Vacation vacation = vacationRepository.getOne(vid);
                vacation.setReject(true);
                vacation.setRejectMemo(reject.getRejectMemo());

                if (vacation.isApproval1() && vacation.isApproval2()) return new RES_rejectVacation("RV004");

                if (commonUtil.checkLevel() == 2) {
                    Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                    Long departmentId = member.getDepartment().getId();
                    if (!vacation.getAuthor().getDepartment().getId().equals(departmentId)) return new RES_rejectVacation("RV003");
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
    public RES_readVacationSummary readVacationSummary() {
        try {
            String memberId = getMemberId();
            int vacation; //휴가
            LocalDateTime todayS = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
            LocalDateTime todayE = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

            if (commonUtil.checkLevel() == 2) {
                Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
                Department department = member.getDepartment();
                vacation = (int) vacationRepository.count(VS.delFalse().and(VS.did(department.getId())).and(VS.startDateAfter(todayS)).and(VS.endDateBefore(todayE).and(VS.approve1Is(true)).and(VS.approve2Is(true))));
            } else if (3 <= commonUtil.checkLevel()) {
                vacation = (int) vacationRepository.count(VS.delFalse().and(VS.startDateAfter(todayS)).and(VS.endDateBefore(todayE).and(VS.approve1Is(true)).and(VS.approve2Is(true))));
            } else {
                return new RES_readVacationSummary("RVS003");
            }
            return new RES_readVacationSummary("RVS001", new VacationSummaryDTO(vacation));
        } catch (Exception e) {
            return new RES_readVacationSummary("RVS002", e.getMessage());
        }
    }

    //권한 체크 및 승인여부 변경
    private boolean approveCheck(Vacation vacation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (commonUtil.checkLevel() == 2) {
            vacation.setApproval1(true);
            vacation.setApprover1(getMember());
            return true;
        } else if (3 <= commonUtil.checkLevel()) {
            if (!vacation.isApproval1()) {
                vacation.setApproval1(true);
                vacation.setApprover1(getMember());
            }
            vacation.setApproval2(true);
            vacation.setApprover2(getMember());
            return true;
        }
        return false;
    }

    //맴버 아이디로 부서 아이디 가져오기
    private Department getDepartment(String memberId) {
        try {
            return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId).getDepartment();
        } catch (Exception e) {
            return null;
        }
    }

    //맴버 아이디로 맴어 이름 가져오기
    private String getMemberName(String memberId) {
        try {
            return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId).getMemberName();
        } catch (Exception e) {
            return "UnFoundMember";
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


