package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.department.*;
import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.DepartmentRepository;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.account.util.DepartmentUtil;
import knk.erp.api.shlee.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentUtil departmentUtil;

    //2021-06-07 15:07 이상훈 추가
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    // 부서 목록에 새로운 부서 추가
    @Transactional
    public Create_DepartmentDTO_RES createDepartment(DepartmentDTO_REQ departmentDTOReq){
        try{
            Department department = departmentDTOReq.toDepartment();
            departmentRepository.save(department);
            return new Create_DepartmentDTO_RES("CD001");
        }catch(Exception e){
            return new Create_DepartmentDTO_RES("CD002", e.getMessage());
        }
    }

    // 부서 목록 읽어오기
    @Transactional(readOnly = true)
    public Read_DepartmentDTO_RES readDepartment(){
        try{
            List<Department> departmentList = departmentRepository.findAllByDeletedIsFalse();
            return new Read_DepartmentDTO_RES("RD001", departmentUtil.getDepartmentList(departmentList));
        }catch(Exception e){
            return new Read_DepartmentDTO_RES("RD002", e.getMessage());
        }
    }

    // 부서 목록 수정
    @Transactional
    public Update_DepartmentDTO_RES updateDepartment(DepartmentDTO_REQ departmentDTOReq){
        try{
            Department department = departmentRepository.getOne(departmentDTOReq.getDep_id());
            department.setDepartmentName(departmentDTOReq.getDepartmentName());
            departmentRepository.save(department);
            return new Update_DepartmentDTO_RES("UD001");
        }catch(Exception e){
            return new Update_DepartmentDTO_RES("UD002", e.getMessage());
        }
    }

    // 부서 목록에서 부서 삭제
    @Transactional
    public Delete_DepartmentDTO_RES deleteDepartment(DepartmentDTO_REQ departmentDTOReq){
        try{
            Department department = departmentRepository.getOne(departmentDTOReq.getDep_id());
            department.setDeleted(true);
            return new Delete_DepartmentDTO_RES("DD001");
        }catch(Exception e){
            return new Delete_DepartmentDTO_RES("DD002", e.getMessage());
        }
    }

    /**2021-06-07 15:07 이상훈 추가
     토큰 받아 해당 직원의 부서 명과 부서인원 리턴**/
    @Transactional
    public RES_DepNameAndMemCount readDepartmentNameAndMemberCount(String token){
        try {
            Authentication authentication = tokenProvider.getAuthentication(token);

            if(checkMaster(authentication)){
                return new RES_DepNameAndMemCount("RDAM001", new DepartmentNameAndMemberCountDTO("구이앤금우통신", (int) memberRepository.count()));
            }

            String memberId = authentication.getName();
            Optional<Member> member = memberRepository.findByMemberId(memberId);

            if(member.isPresent()){
                Department department = member.get().getDepartment();
                String depName = department.getDepartmentName();
                int countOfMember = department.getMemberList().size();
                return new RES_DepNameAndMemCount("RDAM001", new DepartmentNameAndMemberCountDTO(depName, countOfMember));
            }
        }catch (Exception e){
            return new RES_DepNameAndMemCount("RDAM002", e.getMessage());
        }
        return new RES_DepNameAndMemCount("RDAM002", "");
    }
    private boolean checkMaster(Authentication authentication){
        String lvl = authentication.getAuthorities().toString().replace("[ROLE_", "").replace("]", "");
        return lvl.equals("LVL3") || lvl.equals("LVL4") || lvl.equals("ADMIN");
    }
}
