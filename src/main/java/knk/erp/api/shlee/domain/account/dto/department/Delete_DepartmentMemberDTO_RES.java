package knk.erp.api.shlee.domain.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Delete_DepartmentMemberDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("DDM001", "부서 멤버 삭제 성공");
        this.help_codeList.put("DDM002", "부서 멤버 삭제 실패");
        this.help_codeList.put("DDM003", "부서 멤버 삭제 실패 - 삭제하려는 멤버가 해당 부서의 리더임");
    }

    //error
    public Delete_DepartmentMemberDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Delete_DepartmentMemberDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
