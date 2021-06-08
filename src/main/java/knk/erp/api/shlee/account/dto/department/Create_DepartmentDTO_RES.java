package knk.erp.api.shlee.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Create_DepartmentDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CD001", "부서 생성 성공");
        this.help_codeList.put("CD002", "부서 생성 실패");
        this.help_codeList.put("CD003", "부서 생성 실패 - 이미 존재하는 부서 이름");
    }

    //error
    public Create_DepartmentDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Create_DepartmentDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
