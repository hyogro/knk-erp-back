package knk.erp.api.shlee.account.util;

import knk.erp.api.shlee.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.account.entity.Department;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentUtil {
    public List<DepartmentDTO_REQ> getDepartmentList(List<Department> departmentList){
        List<DepartmentDTO_REQ> Department_List = new ArrayList<>();

        for(Department department : departmentList){
            Department_List.add(new DepartmentDTO_REQ(department.getId(), department.getDepartmentName()));
        }

        return Department_List;
    }
}
