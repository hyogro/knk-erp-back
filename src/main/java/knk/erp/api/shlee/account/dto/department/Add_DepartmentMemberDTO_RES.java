package knk.erp.api.shlee.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Add_DepartmentMemberDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("ADM001", "부서 멤버 추가 성공");
        this.help_codeList.put("ADM002", "부서 멤버 추가 실패");
    }

    //error
    public Add_DepartmentMemberDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Add_DepartmentMemberDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
