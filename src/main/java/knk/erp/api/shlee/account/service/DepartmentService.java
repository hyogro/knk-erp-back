package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.department.*;
import knk.erp.api.shlee.account.entity.*;
import knk.erp.api.shlee.account.util.DepartmentUtil;
import knk.erp.api.shlee.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentUtil departmentUtil;

    //2021-06-07 15:07 이상훈 추가
    private final MemberRepository memberRepository;
    //2021-06-08 09:10 이상훈 추가
    private final CommonUtil commonUtil;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 부서 목록에 새로운 부서 추가
    @Transactional
    public Create_DepartmentDTO_RES createDepartment(DepartmentDTO_REQ departmentDTOReq) {
        try {
            if(departmentRepository.existsByDepartmentNameAndDeletedIsFalse(departmentDTOReq.getDepartmentName())) {
                return new Create_DepartmentDTO_RES("CD003", "이미 존재하는 부서입니다.");
            }

            Department department = departmentDTOReq.toDepartment();
            departmentRepository.save(department);

            return new Create_DepartmentDTO_RES("CD001");
        } catch (Exception e) {
            return new Create_DepartmentDTO_RES("CD002", e.getMessage());
        }
    }

    // 부서 목록 읽어오기
    @Transactional(readOnly = true)
    public Read_DepartmentDTO_RES readDepartment() {
        try {
            List<Department> departmentList = departmentRepository.findAllByDeletedIsFalse();

            return new Read_DepartmentDTO_RES("RD001", departmentUtil.getDepartmentList(departmentList));
        } catch (Exception e) {
            return new Read_DepartmentDTO_RES("RD002", e.getMessage());
        }
    }

    // 부서 목록 수정
    @Transactional
    public Update_DepartmentDTO_RES updateDepartment(DepartmentDTO_REQ departmentDTOReq) {
        try {
            if(departmentRepository.existsByDepartmentNameAndDeletedIsFalse(departmentDTOReq.getDepartmentName())){
                return new Update_DepartmentDTO_RES("UD003", "이미 존재하는 부서입니다.");
            }

            Department department = departmentRepository.getOne(departmentDTOReq.getDep_id());
            department.setDepartmentName(departmentDTOReq.getDepartmentName());
            departmentRepository.save(department);

            return new Update_DepartmentDTO_RES("UD001");
        } catch (Exception e) {
            return new Update_DepartmentDTO_RES("UD002", e.getMessage());
        }
    }

    // 부서 목록에서 부서 삭제
    @Transactional
    public Delete_DepartmentDTO_RES deleteDepartment(DepartmentDTO_REQ departmentDTOReq) {
        try {
            Department department = departmentRepository.getOne(departmentDTOReq.getDep_id());
            department.setDeleted(true);
            departmentRepository.save(department);

            return new Delete_DepartmentDTO_RES("DD001");
        } catch (Exception e) {
            return new Delete_DepartmentDTO_RES("DD002", e.getMessage());
        }
    }

    // 부서 리더 수정
    @Transactional
    public UpdateLeader_DepartmentDTO_RES updateLeader(Update_DepartmentLeaderDTO_REQ updateDepartmentLeaderDTOReq){
        try{
            Department department = departmentRepository.getOne(updateDepartmentLeaderDTOReq.getDep_id());
            Member leader;

            if(memberRepository.existsByMemberId(updateDepartmentLeaderDTOReq.getMemberId())){
                leader = memberRepository.findAllByMemberIdAndDeletedIsFalse(updateDepartmentLeaderDTOReq.getMemberId());
            }
            else return new UpdateLeader_DepartmentDTO_RES("ULD004", "입력한 멤버가 존재하지 않음");

            if(department.getLeader() != null){
                Member previous_leader = department.getLeader();

                if(previous_leader.getAuthority().equals(Authority.ROLE_LVL2)){
                    previous_leader.setAuthority(Authority.ROLE_LVL1);
                    memberRepository.save(previous_leader);
                }
            }

            if(leader.getDepartment() == department){
                department.setLeader(leader);

                if(leader.getAuthority().equals(Authority.ROLE_LVL1)){
                    leader.setAuthority(Authority.ROLE_LVL2);
                    memberRepository.save(leader);
                }

                departmentRepository.save(department);

                return new UpdateLeader_DepartmentDTO_RES("ULD001");
            }

            else return new UpdateLeader_DepartmentDTO_RES("ULD003", "리더로 지정하려는 멤버가 해당 부서의 멤버가 아닙니다.");
        }catch (Exception e){
            return new UpdateLeader_DepartmentDTO_RES("ULD002", e.getMessage());
        }
    }

    /**
     * 2021-06-07 15:07 이상훈 추가
     * 토큰 받아 해당 직원의 부서 명과 부서인원 리턴
     **/
    @Transactional
    public RES_DepNameAndMemCount readDepartmentNameAndMemberCount() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (3 <= commonUtil.checkMaster(authentication)) {
                return new RES_DepNameAndMemCount("RDAM001", new DepartmentNameAndMemberCountDTO("구이앤금우통신",
                        (int) memberRepository.count()));
            }

            String memberId = authentication.getName();
            Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);

            Department department = member.getDepartment();

            if(department.getDepartmentName() == null) {
                return new RES_DepNameAndMemCount("RDAM003", "부서에 속해있지않습니다.");
            }

            String depName = department.getDepartmentName();
            int countOfMember = department.getMemberList().size();

            return new RES_DepNameAndMemCount("RDAM001", new DepartmentNameAndMemberCountDTO(depName, countOfMember));
        } catch (Exception e) {
            return new RES_DepNameAndMemCount("RDAM002", e.getMessage());
        }
    }
}
