package knk.erp.api.shlee.domain.account.service;

import knk.erp.api.shlee.domain.account.dto.department.*;
import knk.erp.api.shlee.domain.account.entity.*;
import knk.erp.api.shlee.common.util.AuthorityUtil;
import knk.erp.api.shlee.domain.schedule.entity.Time;
import knk.erp.api.shlee.exception.exceptions.Account.AccountNotFoundMemberException;
import knk.erp.api.shlee.exception.exceptions.Account.AccountTargetIsLeaderException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentExistsBelongMemberException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentNotBelongMemberException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentNotFoundException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentOverlapException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;
    private final AuthorityUtil authorityUtil;

    /* 부서 목록에 새로운 부서 추가 */
    @Transactional
    public void createDepartment(DepartmentDTO_REQ departmentDTOReq) {
        throwIfOverlapDepartmentName(departmentDTOReq.getDepartmentName());

        Department department = departmentDTOReq.toDepartment();
        departmentRepository.save(department);
    }

    // 동명의 부서가 존재할 경우 예외처리
    public void throwIfOverlapDepartmentName(String departmentName){
        if(departmentRepository.existsByDepartmentNameAndDeletedFalse(departmentName)) {
            throw new DepartmentOverlapException();
        }
    }

    /* 부서 목록 읽어오기 */
    @Transactional
    public List<Read_DepartmentDTO> readDepartment() {
        List<Department> departmentList = departmentRepository.findAllByDeletedFalse();
        return departmentList.stream().map(Read_DepartmentDTO::new).collect(Collectors.toList());
    }

    /* 부서 상세 보기 */
    @Transactional
    public ReadDetail_DepartmentDTO_RES readDetailDepartment(Long dep_id){
        throwIfNotFoundDepartment(dep_id);

        Department department = departmentRepository.findByIdAndDeletedFalse(dep_id);
        department.getMemberList().removeIf(Time::isDeleted);

        return new ReadDetail_DepartmentDTO_RES(new ReadDetail_DepartmentDTO(department),
                department.getMemberList().stream().map(Read_DepartmentMemberListDTO::new).collect(Collectors.toList()));
    }

    // 삭제되었거나 존재하지않는 부서 예외 처리
    public void throwIfNotFoundDepartment(Long dep_id){
        if(!departmentRepository.existsByIdAndDeletedFalse(dep_id)){
            throw new DepartmentNotFoundException();
        }
    }

    /* 해당 부서의 부서원을 제외한 모든 직원 리스트 */
    @Transactional
    public List<Read_DepartmentMemberListDTO> readNotThisDepartmentMember(Long dep_id){
        throwIfNotFoundDepartment(dep_id);

        Department department = departmentRepository.findByIdAndDeletedFalse(dep_id);
        List<Department> all = departmentRepository.findAllByDeletedFalse();
        all.remove(department);

        List<Member> otherMembers = new ArrayList<>();

        for(Department d : all){
            d.getMemberList().removeIf(Time::isDeleted);
            otherMembers.addAll(d.getMemberList());
        }

        return otherMembers.stream().map(Read_DepartmentMemberListDTO::new).collect(Collectors.toList());
    }

    /* 부서 이름 수정 */
    @Transactional
    public void updateDepartment(Long dep_id, DepartmentDTO_REQ departmentDTOReq) {
        throwIfNotFoundDepartment(dep_id);
        throwIfOverlapDepartmentName(departmentDTOReq.getDepartmentName());

        Department department = departmentRepository.findByIdAndDeletedFalse(dep_id);
        department.setDepartmentName(departmentDTOReq.getDepartmentName());
        departmentRepository.save(department);
    }

    /* 부서 리더 수정 */
    @Transactional
    public void updateLeader(Long dep_id, Update_DepartmentLeaderDTO_REQ updateDepartmentLeaderDTOReq){
        throwIfNotFoundDepartment(dep_id);

        Department department = departmentRepository.findByIdAndDeletedFalse(dep_id);

        throwIfNotFoundUser(updateDepartmentLeaderDTOReq.getMemberId());

        Member leader = memberRepository.findAllByMemberIdAndDeletedIsFalse(updateDepartmentLeaderDTOReq.getMemberId());

        throwIfNotBelongMember(leader, department);
        if(department.getLeader() != null) { adjustPreviousLeaderAuthority(department.getLeader()); }
        adjustPresentLeaderAuthority(leader);

        department.setLeader(leader);
        departmentRepository.save(department);
    }

    // 새로 지정된 리더의 권한 조정
    public void adjustPresentLeaderAuthority(Member leader){
        if(leader.getAuthority().equals(Authority.ROLE_LVL1) || leader.getAuthority().equals(Authority.ROLE_MANAGE)){
            leader.setAuthority(Authority.ROLE_LVL2);
            memberRepository.save(leader);
        }
    }

    // 이전 리더의 권한 조정
    public void adjustPreviousLeaderAuthority(Member previous_leader){
        if(previous_leader.getAuthority().equals(Authority.ROLE_LVL2) || previous_leader.getAuthority().equals(Authority.ROLE_MATERIALS)){
            previous_leader.setAuthority(Authority.ROLE_LVL1);
            memberRepository.save(previous_leader);
        }
    }

    // 삭제되었거나 존재하지않는 멤버 예외처리
    public void throwIfNotFoundUser(String memberId){
        if(!memberRepository.existsByMemberIdAndDeletedFalse(memberId)){
            throw new AccountNotFoundMemberException();
        }
    }

    // 해당 부서에 존재하지않는 멤버를 리더로 지정하려할 경우 예외처리
    public void throwIfNotBelongMember(Member leader, Department department){
        if(leader.getDepartment() != department) {
            throw new DepartmentNotBelongMemberException();
        }
    }

    /* 부서 목록에서 부서 삭제 */
    @Transactional
    public void deleteDepartment(Long dep_id) {
        throwIfNotFoundDepartment(dep_id);

        Department target = departmentRepository.findByIdAndDeletedFalse(dep_id);
        target.getMemberList().removeIf(Time::isDeleted);

        if (target.getMemberList().size() == 1 && target.getMemberList().get(0) == target.getLeader()) {
            adjustPreviousLeaderAuthority(target.getLeader());
            target.getLeader().setDepartment(departmentRepository.findByDepartmentNameAndDeletedFalse("부서미지정"));
            memberRepository.save(target.getLeader());
            target.setLeader(null);
            target.setMemberList(null);
        }

        throwIfExistsBelongMember(target);

        target.setDeleted(true);
        departmentRepository.save(target);
    }

    // 부서 삭제 시, 부서에 속해있는 멤버가 존재할 경우 예외처리
    public void throwIfExistsBelongMember(Department department) {
        if(department.getMemberList().size()>=1) {
            throw new DepartmentExistsBelongMemberException();
        }
    }

    /* 부서 멤버 추가 */
    @Transactional
    public void addMemberToDepartment(Long dep_id, DepartmentMemberDTO_REQ departmentMemberDTOReq){
        throwIfNotFoundDepartment(dep_id);
        Department targetDepartment = departmentRepository.findByIdAndDeletedFalse(dep_id);

        throwIfNotFoundUser(departmentMemberDTOReq.getMemberId());
        Member targetMember = memberRepository.findByMemberIdAndDeletedIsFalse(departmentMemberDTOReq.getMemberId());

        throwIfTargetIsLeader(targetMember, targetMember.getDepartment());

        targetMember.setDepartment(targetDepartment);
        memberRepository.save(targetMember);
    }

    // 대상 부서원이 부서의 리더일 경우 예외 처리
    public void throwIfTargetIsLeader(Member targetMember, Department targetDepartment){
        if(targetDepartment.getLeader() == targetMember){
            throw new AccountTargetIsLeaderException();
        }
    }

    /* 부서 멤버 삭제 */
    @Transactional
    public void deleteMemberToDepartment(String memberId){
        throwIfNotFoundUser(memberId);
        Member targetMember = memberRepository.findByMemberIdAndDeletedIsFalse(memberId);
        Department targetDepartment = targetMember.getDepartment();

        throwIfTargetIsLeader(targetMember, targetDepartment);

        if(!departmentRepository.existsByDepartmentNameAndDeletedFalse("부서미지정")) {
            DepartmentDTO_REQ departmentDTO_req = new DepartmentDTO_REQ("부서미지정", false);
            departmentDTO_req.toDepartment();
        }

        targetMember.setDepartment(departmentRepository.findByDepartmentNameAndDeletedFalse("부서미지정"));
        memberRepository.save(targetMember);
    }

    /* 토큰 받아 해당 직원의 부서 명과 부서인원 리턴 */
    @Transactional
    public DepartmentNameAndMemberCountDTO readDepartmentNameAndMemberCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (3 <= authorityUtil.checkLevel()) {
            return new DepartmentNameAndMemberCountDTO("구이앤금우통신",
                    memberRepository.countAllByMemberNameIsNot("관리자").intValue());
        }

        String memberId = authentication.getName();
        Member member = memberRepository.findAllByMemberIdAndDeletedIsFalse(memberId);

        throwIfNotFoundDepartment(member.getDepartment().getId());
        Department department = member.getDepartment();

        return new DepartmentNameAndMemberCountDTO(department.getDepartmentName(), department.getMemberList().size());
    }

    /* 조직도 불러오기 */
    @Transactional
    public List<OrganizationChartDTO> readOrganizationChart(){
        List<Department> all = departmentRepository.findAllByDeletedFalse();
        all.removeIf(d -> d.getDepartmentName().equals("부서미지정"));
        List<OrganizationChartDTO> chartList = new ArrayList<>();
        for(Department d : all){
            List<OrganizationChartMemberInfoDTO> memberList = new ArrayList<>();
            List<Member> dMember = d.getMemberList();
            for(Member m : dMember){
                memberList.add(new OrganizationChartMemberInfoDTO(m.getMemberName(), m.getPosition(), m.getPhone()));
            }
            chartList.add(new OrganizationChartDTO(d.getDepartmentName(), memberList));
        }

        return sortChartList(chartList);
    }

    // 조직도 순서 정렬
    public List<OrganizationChartDTO> sortChartList(List<OrganizationChartDTO> chartList){
        OrganizationChartDTO temp;
        OrganizationChartDTO chart;

        for(int i = 0; i< chartList.size(); i++){
            chart = chartList.get(i);
            switch (chart.getDepartmentName()) {
                case "운영부":
                    temp = chartList.get(0);
                    chartList.set(0, chart);
                    chartList.set(i, temp);
                    break;
                case "사업부":
                    temp = chartList.get(1);
                    chartList.set(1, chart);
                    chartList.set(i, temp);
                    break;
                case "기술":
                    temp = chartList.get(2);
                    chartList.set(2, chart);
                    chartList.set(i, temp);
                    break;
            }
        }
        return chartList;
    }
}
