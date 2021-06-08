package knk.erp.api.shlee.account.dto.account;

import knk.erp.api.shlee.common.dto.TokenDto;
import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_AccountDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("UA001", "회원정보 수정 성공");
        this.help_codeList.put("UA002", "회원정보 수정 실패");
        this.help_codeList.put("UA003", "회원정보 수정 실패 - 권한 부족");
        this.help_codeList.put("UA004", "회원정보 수정 실패 - 수정할 멤버가 존재하지 않습니다.");
        this.help_codeList.put("UA005", "회원정보 수정 실패 - 대상이 부서의 리더입니다.");
    }

    //error
    public Update_AccountDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Update_AccountDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
