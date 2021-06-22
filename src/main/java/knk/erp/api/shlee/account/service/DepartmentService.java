package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.department.*;
import knk.erp.api.shlee.account.entity.*;
import knk.erp.api.shlee.account.util.DepartmentUtil;
import knk.erp.api.shlee.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    // 부서 목록에 새로운 부서 추가
    @Transactional
    public Create_DepartmentDTO_RES createDepartment(DepartmentDTO_REQ departmentDTOReq) {
        try {
            if(departmentRepository.existsByDepartmentNameAndDeletedFalse(departmentDTOReq.getDepartmentName())) {
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
    @Transactional
    public Read_DepartmentDTO_RES readDepartment() {
        try {
            List<Department> departmentList = departmentRepository.findAllByDeletedFalse();
            List<String> departmentName = new ArrayList<>();
            List<Long> dep_id = new ArrayList<>();
            for(Department d : departmentList){
                departmentName.add(d.getDepartmentName());
                dep_id.add(d.getId());
            }

            return new Read_DepartmentDTO_RES("RD001", new Read_DepartmentDTO(dep_id, departmentName));
        } catch (Exception e) {
            return new Read_DepartmentDTO_RES("RD002", e.getMessage());
        }
    }

    @Transactional
    public ReadDetail_DepartmentDTO_RES readDetailDepartment(Long dep_id){
        Department department = departmentRepository.getOne(dep_id);
        try{
            List<String> memberName = new ArrayList<>();
            for(Member m : department.getMemberList()){
                memberName.add(m.getMemberName());
            }
            return new ReadDetail_DepartmentDTO_RES("RDD001", new ReadDetail_DepartmentDTO(department.getDepartmentName(),
                    memberName, department.getLeader().getMemberName()));
        }catch(Exception e){
            return new ReadDetail_DepartmentDTO_RES("RDD002", e.getMessage());
        }
    }

    // 부서 이름 수정
    @Transactional
    public Update_DepartmentDTO_RES updateDepartment(Long dep_id, DepartmentDTO_REQ departmentDTOReq) {
        try {
            if(departmentRepository.existsByDepartmentNameAndDeletedFalse(departmentDTOReq.getDepartmentName())){
                return new Update_DepartmentDTO_RES("UD003", "이미 존재하는 부서입니다.");
            }

            Department department = departmentRepository.getOne(dep_id);
            department.setDepartmentName(departmentDTOReq.getDepartmentName());
            departmentRepository.save(department);

            return new Update_DepartmentDTO_RES("UD001");
        } catch (Exception e) {
            return new Update_DepartmentDTO_RES("UD002", e.getMessage());
        }
    }

    // 부서 리더 수정
    @Transactional
    public UpdateLeader_DepartmentDTO_RES updateLeader(Long dep_id, Update_DepartmentLeaderDTO_REQ updateDepartmentLeaderDTOReq){
        try{
            Department department = departmentRepository.getOne(dep_id);
            Member leader;

            if(memberRepository.existsByMemberIdAndDeletedFalse(updateDepartmentLeaderDTOReq.getMemberId())){
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

    // 부서 목록에서 부서 삭제
    @Transactional
    public Delete_DepartmentDTO_RES deleteDepartment(Long dep_id) {
        try {
            Department target = departmentRepository.getOne(dep_id);
            List<Member> memberList =  target.getMemberList();

            for(Member m : memberList){
                m.setDepartment(departmentRepository.findByDepartmentNameAndDeletedFalse("부서미지정"));
            }

            if(target.getLeader().getAuthority().equals(Authority.ROLE_LVL2)){
                target.getLeader().setAuthority(Authority.ROLE_LVL1);
                memberRepository.save(target.getLeader());
            }

            target.setLeader(null);
            target.setDeleted(true);
            departmentRepository.save(target);

            return new Delete_DepartmentDTO_RES("DD001");
        } catch (Exception e) {
            return new Delete_DepartmentDTO_RES("DD002", e.getMessage());
        }
    }

    // 부서 멤버 추가
    @Transactional
    public Add_DepartmentMemberDTO_RES addMemberToDepartment(Long dep_id, DepartmentMemberDTO_REQ departmentMemberDTOReq){
        try{
            Department targetDepartment = departmentRepository.findByIdAndDeletedFalse(dep_id);
            Member targetMember = memberRepository.findByMemberIdAndDeletedIsFalse(departmentMemberDTOReq.getMemberId());
            targetMember.setDepartment(targetDepartment);
            memberRepository.save(targetMember);

            return new Add_DepartmentMemberDTO_RES("ADM001");
        }catch(Exception e){
            return new Add_DepartmentMemberDTO_RES("ADM002", e.getMessage());
        }
    }

    // 부서 멤버 삭제
    @Transactional
    public Delete_DepartmentMemberDTO_RES deleteMemberToDepartment(Long dep_id, String memberId){
        try{
            Department targetDepartment = departmentRepository.findByIdAndDeletedFalse(dep_id);
            Member targetMember = memberRepository.findByMemberIdAndDeletedIsFalse(memberId);

            if(targetDepartment.getLeader() == targetMember){
                return new Delete_DepartmentMemberDTO_RES("DDM003","해당 부서의 리더");
            }

            targetMember.setDepartment(departmentRepository.findByDepartmentNameAndDeletedFalse("부서미지정"));
            memberRepository.save(targetMember);

            return new Delete_DepartmentMemberDTO_RES("DDM001");
        }catch(Exception e){
            return new Delete_DepartmentMemberDTO_RES("DDm002", e.getMessage());
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

            if (3 <= commonUtil.checkLevel()) {
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
