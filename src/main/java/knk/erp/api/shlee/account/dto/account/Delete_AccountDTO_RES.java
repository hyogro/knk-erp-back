package knk.erp.api.shlee.account.dto.account;

import knk.erp.api.shlee.common.dto.TokenDto;
import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Delete_AccountDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("DA001", "회원정보 삭제 성공");
        this.help_codeList.put("DA002", "회원정보 삭제 실패");
        this.help_codeList.put("DA003", "회원정보 삭제 실패 - 권한 부족");
        this.help_codeList.put("DA004", "회원정보 삭제 실패 - 대상이 존재하지 않습니다.");
    }

    //error
    public Delete_AccountDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Delete_AccountDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
