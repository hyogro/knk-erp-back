package knk.erp.api.shlee.domain.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLeader_DepartmentDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("ULD001", "부서 팀장 수정 성공");
        this.help_codeList.put("ULD002", "부서 팀장 수정 실패");
        this.help_codeList.put("ULD003", "부서 팀장 수정 실패 - 리더로 지정하려는 멤버가 해당 부서의 멤버가 아닙니다.");
        this.help_codeList.put("ULD004", "입력한 멤버가 존재하지않음.");
    }

    // error
    public UpdateLeader_DepartmentDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public UpdateLeader_DepartmentDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
