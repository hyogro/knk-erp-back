package knk.erp.api.shlee.schedule.service;

import knk.erp.api.shlee.account.entity.DepartmentRepository;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.common.util.CommonUtil;
import knk.erp.api.shlee.schedule.dto.Vacation.RES_createVacation;
import knk.erp.api.shlee.schedule.dto.Vacation.VacationDTO;
import knk.erp.api.shlee.schedule.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VacationRepository vacationRepository;
    private final CommonUtil commonUtil;

    /**
     * 권한 여부 체크를 위한 사용자, 부서 리포지토리 접근
     **/
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    public RES_createVacation createVacation(VacationDTO vacationDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();
            vacationDTO.setMemberId(memberId);
            vacationDTO.setDepartmentId(getDepartmentIdByMemberId(memberId));
            vacationRepository.save(vacationDTO.toEntity());
            return new RES_createVacation("CV001");
        }catch (Exception e){
            return new RES_createVacation("CV002", e.getMessage());
        }
    }
    //맴버 아이디로 부서 아이디 가져오기
    private Long getDepartmentIdByMemberId(String memberId) {
        try {
            return memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId).getDepartment().getId();
        }catch (Exception e){
            return -1L;
        }
    }

}


