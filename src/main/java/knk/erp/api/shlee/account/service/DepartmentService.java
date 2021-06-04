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

    @Transactional
    public Create_DepartmentDTO_RES create(DepartmentDTO_REQ departmentDTOReq){
        Department department = departmentDTOReq.toDepartment();
        try{
            departmentRepository.save(department);
            return new Create_DepartmentDTO_RES("CD001");
        }catch(Exception e){
            return new Create_DepartmentDTO_RES("CD002", e.getMessage());
        }
    }

    @Transactional
    public Read_DepartmentDTO_RES read(){
        try{
            System.out.println("123123");
            List<Department> departmentList = departmentRepository.findAll();
            System.out.println("123123");
            return new Read_DepartmentDTO_RES("RD001", departmentUtil.getDepartmentList(departmentList));
        }catch(Exception e){
            return new Read_DepartmentDTO_RES("RD002", e.getMessage());
        }
    }
    /*
    @Transactional
    public Update_DepartmentDTO_RES update(DepartmentDTO_REQ departmentDTOReq){
        try{

        }catch(Exception e){

        }
    }

    @Transactional
    public Delete_DepartmentDTO_RES delete(DepartmentDTO_REQ departmentDTOReq){
        try{

        }catch(Exception e){

        }
    }*/
}
