package knk.erp.api.shlee.domain.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Delete_DepartmentDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("DD001", "부서 삭제 성공");
        this.help_codeList.put("DD002", "부서 삭제 실패");
    }

    //error
    public Delete_DepartmentDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Delete_DepartmentDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
