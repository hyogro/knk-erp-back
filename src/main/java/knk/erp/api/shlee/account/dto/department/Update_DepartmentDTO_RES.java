package knk.erp.api.shlee.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_DepartmentDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("UD001", "부서 수정 성공");
        this.help_codeList.put("UD002", "부서 수정 실패");
    }

    // error
    public Update_DepartmentDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Update_DepartmentDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}