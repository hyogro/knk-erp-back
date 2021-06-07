package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.dto.department.*;
import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.DepartmentRepository;
import knk.erp.api.shlee.account.util.DepartmentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentUtil departmentUtil;

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
    @Transactional
    public Read_DepartmentDTO_RES readDepartment(){
        try{
            System.out.println("123123");
            List<Department> departmentList = departmentRepository.findAllByDeletedIsFalse();
            System.out.println("123123");
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
}
