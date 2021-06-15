package knk.erp.api.shlee.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_DepartmentDTO_RES {
    private String code;
    private String message;
    List<String> departmentName;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RD001", "부서 목록 읽기 성공");
        this.help_codeList.put("RD002", "부서 목록 읽기 실패");
    }

    //error
    public Read_DepartmentDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Read_DepartmentDTO_RES(String code, List<String> departmentName) {
        createCodeList();
        this.code = code;
        this.departmentName = departmentName;
    }
}
