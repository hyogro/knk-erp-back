package knk.erp.api.shlee.domain.schedule.service;

import knk.erp.api.shlee.common.util.EntityUtil;
import knk.erp.api.shlee.domain.account.entity.Department;
import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.AuthorityUtil;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final VacationRepository vacationRepository;
    private final AddVacationRepository addVacationRepository;
    private final AuthorityUtil authorityUtil;
    private final VacationUtil util;
    private final MemberRepository memberRepository;

    //추가휴가 생성
    @Transactional
    public void createAddVacation(AddVacationDTO addVacationDTO) {
        AddVacation addVacation = addVacationDTO.toEntity();

        Member receiver = memberRepository.findAllByMemberIdAndDeletedIsFalse(addVacationDTO.getReceiverId());
        Member giver = EntityUtil.getInstance().getMember(memberRepository);

        addVacation.setReceiver(receiver);
        addVacation.setGiver(giver);
        addVacationRepository.save(addVacation);
    }

    //추가휴가 전체 목록조회
    public List<Object> readAddVacationAllList() {
        List<AddVacation> addVacationList = addVacationRepository.findAll(AVS.delFalse());
        return util.AddVacationListToDTO(addVacationList);
    }

    //추가휴가 특정 회원 목록조회
    public List<Object> readAddVacationList(String memberId) {
        List<AddVacation> addVacationList = addVacationRepository.findAll(AVS.delFalse().and(AVS.mid(memberId)));
        return util.AddVacationListToDTO(addVacationList);
    }

    //추가휴가 상세조회
    public AddVacationDetailData readAddVacationDetail(Long avid) {
        AddVacation addVacation = addVacationRepository.getOne(avid);
        return new AddVacationDetailData(addVacation);
    }

    //추가휴가 삭제
    public void deleteAddVacation(Long avid) {
        AddVacation addVacation = addVacationRepository.getOne(avid);
        addVacation.setDeleted(true);
        addVacationRepository.save(addVacation);
    }

    //개인 휴가정보조회
    public VacationInfo readVacationInfo(String memberId) {
        Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);
        LocalDate joiningDate = member.getJoiningDate();
        LocalDate today = LocalDate.now();
        int totalVacation;
        int usedVacation = getUsedVacation(memberId);
        int addVacationVal = 0;
        List<AddVacation> addVacationList = addVacationRepository.findAll(AVS.delFalse().and(AVS.mid(memberId)));

        for (AddVacation addVacation : addVacationList) {
            if (addVacation.getCreateDate().getYear() == today.getYear()) {
                addVacationVal += addVacation.getDate();
            }
        }

        Period period = Period.between(joiningDate, today);

        if (period.getYears() == 0 && joiningDate.getYear() == today.getYear()) {//올해 입사자
            totalVacation = period.getMonths() * 60 * 8; //연차갯수 * 분 * 시간
        } else {//1년 이상 재직자
            int yearGap = today.getYear() - joiningDate.getYear();

            yearGap = (yearGap % 2 == 0) ? yearGap : yearGap + 1;


            totalVacation = (15 + (yearGap / 2) - 1) * 60 * 8; //연차갯수 * 분 * 시간
        }

        return new VacationInfo(totalVacation, usedVacation, addVacationVal);
    }

    //사용된 휴가 시간(분) 조회
    private int getUsedVacation(String memberId) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yearStart = LocalDateTime.of(today.getYear(), 1, 1, 0, 0, 0);
        LocalDateTime yearEnd = LocalDateTime.of(today.getYear() + 1, 1, 1, 0, 0, 0);

        List<Vacation> vacationList = vacationRepository.findAll(
                VS.delFalse()
                        .and(VS.mid(memberId))
                        .and(VS.approve2Is(true))
                        .and(VS.vacationDateBetween(yearStart, yearEnd)));

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
    public void createVacation(VacationDTO vacationDTO) {
        Vacation vacation = vacationDTO.toEntity();
        vacation.setAuthor(EntityUtil.getInstance().getMember(memberRepository));
        approveCheck(vacation);

        vacationRepository.save(vacation);
    }

    //내 휴가목록 조회
    public List<Object> readVacationList() {
        String memberId = EntityUtil.getInstance().getMemberId();
        List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.mid(memberId)), Sort.by(Sort.Direction.DESC, "createDate"));
        return util.VacationListToDTO(vacationList);
    }

    //모든 휴가목록 조회
    public List<Object> readAllVacationList(LocalDateTime sd, LocalDateTime ed) {
        List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse()
                .and(VS.approve1Is(true))
                .and(VS.approve2Is(true))
                .and(VS.vacationDateBetween(sd, ed))
                .and(VS.memberDF())
        );
        return util.VacationListToDTO(vacationList);
    }

    //내 휴가상세 조회
    public VacationDetailData readVacationDetail(Long vid) {
        Vacation vacation = vacationRepository.getOne(vid);
        return new VacationDetailData(vacation);
    }

    //내 휴가 삭제
    public void deleteVacation(Long vid) {
        String memberId = EntityUtil.getInstance().getMemberId();

        Vacation vacation = vacationRepository.getOne(vid);
        //TODO: 1, 2차 승인된경우 예외처리
        //TODO: 본인휴가 아닌경우 예외처리

        vacation.setDeleted(true);
        vacationRepository.save(vacation);
    }

    //승인, 거부할 휴가목록 조회
    public List<Object> readVacationListForManage(LocalDate startDate, LocalDate endDate) {
        List<Vacation> vacationList = new ArrayList<>();
        LocalDateTime sd = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime ed = LocalDateTime.of(endDate, LocalTime.MAX);

        if (authorityUtil.checkLevel() == 2) {
            Member member = EntityUtil.getInstance().getMember(memberRepository);
            assert member != null;
            Long did = member.getDepartment().getId();
            vacationList = vacationRepository.findAll(
                    VS.delFalse().and(VS.memberDF()).and(VS.rejectIs(false)).and(VS.did(did)).and(VS.approve1Is(true)).and(VS.vacationDateBetween(sd, ed))
                            .or(VS.delFalse().and(VS.rejectIs(true)).and(VS.did(did)).and(VS.approve1Is(false)).and(VS.vacationDateBetween(sd, ed)))
                    , Sort.by("createDate").descending());

        } else if (3 <= authorityUtil.checkLevel()) {
            vacationList = vacationRepository.findAll(
                    VS.delFalse().and(VS.memberDF()).and(VS.rejectIs(false)).and(VS.approve2Is(true)).and(VS.vacationDateBetween(sd, ed))
                            .or(VS.delFalse().and(VS.rejectIs(true)).and(VS.approve2Is(false)).and(VS.vacationDateBetween(sd, ed)))
                    , Sort.by("createDate").descending());
        }
        return util.VacationListToDTO(vacationList);
    }

    //승인, 거부할 휴가목록 조회
    public List<Object> readVacationListForApprove() {
        List<Vacation> vacationList = new ArrayList<>();

        if (authorityUtil.checkLevel() == 2) {
            Member member = EntityUtil.getInstance().getMember(memberRepository);
            assert member != null;
            Long did = member.getDepartment().getId();
            vacationList = vacationRepository.findAll(VS.delFalse().and(VS.rejectIs(false)).and(VS.did(did)).and(VS.approve1Is(false))
                    , Sort.by("createDate").descending());

        } else if (3 <= authorityUtil.checkLevel()) {
            vacationList = vacationRepository.findAll(VS.delFalse().and(VS.rejectIs(false)).and(VS.approve2Is(false))
                    , Sort.by("createDate").descending());
        }
        return util.VacationListToDTO(vacationList);
    }

    //휴가 승인
    public void approveVacation(Long vid) {
        Vacation vacation = vacationRepository.getOne(vid);

        if (authorityUtil.checkLevel() == 2) {
            Member member = EntityUtil.getInstance().getMember(memberRepository);
            assert member != null;
            Long departmentId = member.getDepartment().getId();
            if (!vacation.getAuthor().getDepartment().getId().equals(departmentId)) {
                //TODO: 부서 다를경우 예외처리
            }
        }

        boolean isChange = approveCheck(vacation);
        if (isChange) vacationRepository.save(vacation);

    }

    //휴가 거절
    public void rejectVacation(Long vid, REQ_rejectVacation reject) {
        if (2 <= authorityUtil.checkLevel()) {
            Member member = EntityUtil.getInstance().getMember(memberRepository);
            assert member != null;

            Vacation vacation = vacationRepository.getOne(vid);
            vacation.setReject(true);
            vacation.setRejectMemo(member.getMemberName() + ") " + reject.getRejectMemo());

            if (vacation.isApproval1() && vacation.isApproval2()) {
                //TODO: 1, 2차 승인된경우 예외처리
            }

            if (authorityUtil.checkLevel() == 2) {
                Long departmentId = member.getDepartment().getId();
                if (!vacation.getAuthor().getDepartment().getId().equals(departmentId)) {
                    //TODO: 부서 다를경우 예외처리
                }
            }

            vacationRepository.save(vacation);
        }
        //TODO: 레벨 1이하 예외처리
    }

    //권한 체크 및 승인여부 변경
    private boolean approveCheck(Vacation vacation) {
        if (authorityUtil.checkLevel() == 2) {
            vacation.setApproval1(true);
            vacation.setApprover1(EntityUtil.getInstance().getMember(memberRepository));
            return true;
        } else if (3 <= authorityUtil.checkLevel()) {
            if (!vacation.isApproval1()) {
                vacation.setApproval1(true);
                vacation.setApprover1(EntityUtil.getInstance().getMember(memberRepository));
            }
            vacation.setApproval2(true);
            vacation.setApprover2(EntityUtil.getInstance().getMember(memberRepository));
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
}


