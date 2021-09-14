package knk.erp.api.shlee.domain.schedule.service;

import knk.erp.api.shlee.domain.account.entity.Department;
import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.CommonUtil;
import knk.erp.api.shlee.domain.schedule.dto.Vacation.*;
import knk.erp.api.shlee.domain.schedule.entity.AddVacation;
import knk.erp.api.shlee.domain.schedule.entity.Vacation;
import knk.erp.api.shlee.domain.schedule.repository.AddVacationRepository;
import knk.erp.api.shlee.domain.schedule.repository.VacationRepository;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMD;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCMDL;
import knk.erp.api.shlee.domain.schedule.responseEntity.vacation.*;
import knk.erp.api.shlee.domain.schedule.specification.AVS;
import knk.erp.api.shlee.domain.schedule.specification.VS;
import knk.erp.api.shlee.domain.schedule.util.VacationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final VacationRepository vacationRepository;
    private final AddVacationRepository addVacationRepository;
    private final CommonUtil commonUtil;
    private final VacationUtil util;
    private final MemberRepository memberRepository;

    //추가휴가 생성
    public ResponseCM createAddVacation(AddVacationDTO addVacationDTO){
        try {
            AddVacation addVacation = addVacationDTO.toEntity();
            Member receiver = memberRepository.findAllByMemberIdAndDeletedIsFalse(addVacationDTO.getReceiverId());
            Member giver = getMember();
            addVacation.setReceiver(receiver);
            addVacation.setGiver(giver);
            addVacationRepository.save(addVacation);
            return new ResponseCM("CAV001");
        }catch (Exception e){ 
            return new ResponseCM("CAV002", e.getMessage());
        }
    }

    //추가휴가 전체 목록조회
    public ResponseCMDL readAddVacationAllList(){
        try {
            List<AddVacation> addVacationList = addVacationRepository.findAll(AVS.delFalse());
            return new ResponseCMDL("RAVL001", util.AddVacationListToDTO(addVacationList));

        }catch (Exception e){
            return new ResponseCMDL("RAVL002", e.getMessage());
        }
    }

    //추가휴가 특정 회원 목록조회
    public ResponseCMDL readAddVacationList(String memberId){
        try {
            List<AddVacation> addVacationList = addVacationRepository.findAll(AVS.delFalse().and(AVS.mid(memberId)));
            return new ResponseCMDL("RAVL001", util.AddVacationListToDTO(addVacationList));

        }catch (Exception e){
            return new ResponseCMDL("RAVL002", e.getMessage());
        }
    }

    //추가휴가 상세조회
    public ResponseCMD readAddVacationDetail(Long avid){
        try {
            AddVacation addVacation = addVacationRepository.getOne(avid);
            return new ResponseCMD("RAVD001", new AddVacationDetailData(addVacation));

        }catch (Exception e){
            return new ResponseCMD("RAVD002", e.getMessage());
        }
    }

    //추가휴가 삭제
    public ResponseCM deleteAddVacation(Long avid){
        try {
            AddVacation addVacation = addVacationRepository.getOne(avid);
            addVacation.setDeleted(true);
            addVacationRepository.save(addVacation);
            return new ResponseCM("DAV001");
        }catch (Exception e){
            return new ResponseCM("DAV002", e.getMessage());
        }
    }

    //개인 휴가정보조회
    public ResponseCMD readVacationInfo(String memberId) {
        try {
            Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
            LocalDate joiningDate = member.getJoiningDate();
            LocalDate today = LocalDate.now();
            int totalVacation;
            int usedVacation = getUsedVacation(memberId);
            int addVacationVal = 0;
            List<AddVacation> addVacationList = addVacationRepository.findAll(AVS.delFalse().and(AVS.mid(memberId)));
            for(AddVacation addVacation : addVacationList){
                addVacationVal += addVacation.getDate();
            }

            Period period = Period.between(joiningDate, today);

            if (period.getYears() == 0) {//올해 입사자
                totalVacation = period.getMonths() * 60 * 8; //연차갯수 * 분 * 시간
            } else {//1년 이상 재직자
                totalVacation = (15 + ((period.getYears() - 1) / 2)) * 60 * 8; //연차갯수 * 분 * 시간
            }

            return new ResponseCMD("RVI001", new VacationInfo(totalVacation, usedVacation, addVacationVal));
        } catch (Exception e) {
            return new ResponseCMD("RVI002", new VacationInfo(-1, -1, -1));
        }
    }

    //사용된 휴가 시간(분) 조회
    private int getUsedVacation(String memberId) {
        List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.mid(memberId)).and(VS.approve2Is(true)));
        int usedVacation = 0;
        for (Vacation vacation : vacationList) {
            LocalDateTime startDate = vacation.getStartDate();
            LocalDateTime end = vacation.getEndDate();

            switch (vacation.getType()) {
                case "연차":
                    int date = (int) ChronoUnit.DAYS.between(startDate, end) + 1;
                    usedVacation += (date * 8);
                    break;
                case "오후반차":
                case "오전반차":
                    usedVacation += 4;
                    break;
                case "시간제":
                    usedVacation += (int) ChronoUnit.HOURS.between(startDate, end);
                    break;
            }
        }
        return usedVacation * 60;
    }

    //휴가 생성
    public ResponseCM createVacation(VacationDTO vacationDTO) {
        try {
            Vacation vacation = vacationDTO.toEntity();
            vacation.setAuthor(getMember());
            approveCheck(vacation);

            vacationRepository.save(vacation);
            return new ResponseCM("CV001");
        } catch (Exception e) {
            return new ResponseCM("CV002", e.getMessage());
        }
    }

    //내 휴가목록 조회
    public ResponseCMDL readVacationList() {
        try {
            String memberId = getMemberId();
            List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.mid(memberId)));
            return new ResponseCMDL("RVL001", util.VacationListToDTO(vacationList));
        } catch (Exception e) {
            return new ResponseCMDL("RVL002", e.getMessage());
        }
    }

    //모든 휴가목록 조회
    public ResponseCMDL readAllVacationList(LocalDateTime sd, LocalDateTime ed) {
        try {

            List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.approve1Is(true).and(VS.approve2Is(true).and(VS.vacationDateBetween(sd, ed)))));
            return new ResponseCMDL("RVL001", util.VacationListToDTO(vacationList));
        } catch (Exception e) {
            return new ResponseCMDL("RVL002", e.getMessage());
        }
    }

    //내 휴가상세 조회
    public ResponseCMD readVacationDetail(Long vid) {
        try {
            Vacation vacation = vacationRepository.getOne(vid);
            return new ResponseCMD("RVD001", new VacationDetailData(vacation));
        } catch (Exception e) {
            return new ResponseCMD("RVD002", e.getMessage());
        }
    }

    //내 휴가 삭제 dddd
    public ResponseCM deleteVacation(Long vid) {
        try {
            String memberId = getMemberId();

            Vacation vacation = vacationRepository.getOne(vid);
            if (vacation.isApproval1() && vacation.isApproval2()) return new ResponseCM("DV003");
            if (!vacation.getAuthor().getMemberId().equals(memberId)) return new ResponseCM("DV004");

            vacation.setDeleted(true);
            vacationRepository.save(vacation);
            return new ResponseCM("DV001");
        } catch (Exception e) {
            return new ResponseCM("DV002", e.getMessage());
        }
    }

    //승인, 거부할 휴가목록 조회
    public ResponseCMDL readVacationListForManage(LocalDate startDate, LocalDate endDate) {
        try {
            List<Vacation> vacationList = new ArrayList<>();
            LocalDateTime sd = LocalDateTime.of(startDate, LocalTime.MIN);
            LocalDateTime ed = LocalDateTime.of(endDate, LocalTime.MAX);

            if (commonUtil.checkLevel() == 2) {
                Member member = getMember();
                assert member != null;
                Long did = member.getDepartment().getId();
                vacationList = vacationRepository.findAll(
                        VS.delFalse().and(VS.rejectIs(false)).and(VS.did(did)).and(VS.approve1Is(true)).and(VS.vacationDateBetween(sd, ed))
                        .or(VS.delFalse().and(VS.rejectIs(true)).and(VS.did(did)).and(VS.approve1Is(false)).and(VS.vacationDateBetween(sd, ed)))
                        , Sort.by("createDate").descending());

            } else if (3 <= commonUtil.checkLevel()) {
                vacationList = vacationRepository.findAll(
                        VS.delFalse().and(VS.rejectIs(false)).and(VS.approve2Is(true)).and(VS.vacationDateBetween(sd, ed))
                        .or(VS.delFalse().and(VS.rejectIs(true)).and(VS.approve2Is(false)).and(VS.vacationDateBetween(sd, ed)))
                        , Sort.by("createDate").descending());
            }
            return new ResponseCMDL("RVL001", util.VacationListToDTO(vacationList));
        } catch (Exception e) {
            return new ResponseCMDL("RVL002", e.getMessage());
        }
    }

    //승인, 거부할 휴가목록 조회
    public ResponseCMDL readVacationListForApprove() {
        try {
            List<Vacation> vacationList = new ArrayList<>();

            if (commonUtil.checkLevel() == 2) {
                Member member = getMember();
                assert member != null;
                Long did = member.getDepartment().getId();
                vacationList = vacationRepository.findAll(VS.delFalse().and(VS.rejectIs(false)).and(VS.did(did)).and(VS.approve1Is(false))
                        , Sort.by("createDate").descending());

            } else if (3 <= commonUtil.checkLevel()) {
                vacationList = vacationRepository.findAll(VS.delFalse().and(VS.rejectIs(false)).and(VS.approve2Is(false))
                        , Sort.by("createDate").descending());
            }
            return new ResponseCMDL("RVL001", util.VacationListToDTO(vacationList));
        } catch (Exception e) {
            return new ResponseCMDL("RVL002", e.getMessage());
        }
    }

    //휴가 승인
    public ResponseCM approveVacation(Long vid) {
        try {
            Vacation vacation = vacationRepository.getOne(vid);

            if (commonUtil.checkLevel() == 2) {
                Member member = getMember();
                assert member != null;
                Long departmentId = member.getDepartment().getId();
                if (!vacation.getAuthor().getDepartment().getId().equals(departmentId)) return new ResponseCM("AV003");
            }

            boolean isChange = approveCheck(vacation);
            if (isChange) vacationRepository.save(vacation);

            return isChange ? new ResponseCM("AV001") : new ResponseCM("AV003");

        } catch (Exception e) {
            return new ResponseCM("AV002", e.getMessage());
        }
    }

    //휴가 거절
    public ResponseCM rejectVacation(Long vid, REQ_rejectVacation reject) {
        try {

            if (2 <= commonUtil.checkLevel()) {
                Member member = getMember();
                assert member != null;

                Vacation vacation = vacationRepository.getOne(vid);
                vacation.setReject(true);
                vacation.setRejectMemo(member.getMemberName() + ") " + reject.getRejectMemo());

                if (vacation.isApproval1() && vacation.isApproval2()) return new ResponseCM("RV004");

                if (commonUtil.checkLevel() == 2) {
                    Long departmentId = member.getDepartment().getId();
                    if (!vacation.getAuthor().getDepartment().getId().equals(departmentId))
                        return new ResponseCM("RV003");
                }

                vacationRepository.save(vacation);
                return new ResponseCM("RV001");
            }
            return new ResponseCM("RV003");

        } catch (Exception e) {
            return new ResponseCM("RV002", e.getMessage());
        }
    }

    //권한 체크 및 승인여부 변경
    private boolean approveCheck(Vacation vacation) {
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


