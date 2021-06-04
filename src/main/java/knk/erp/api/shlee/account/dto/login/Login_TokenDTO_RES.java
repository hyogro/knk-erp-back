package knk.erp.api.shlee.account.dto.login;

import knk.erp.api.shlee.common.dto.TokenDto;
import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Login_TokenDTO_RES {
    private String code;
    private String message;
    private TokenDto tokenDto;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("LI001", "로그인 성공");
        this.help_codeList.put("LI002", "로그인 실패");
    }

    //error
    public Login_TokenDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Login_TokenDTO_RES(String code, TokenDto tokenDto) {
        createCodeList();
        this.code = code;
        this.tokenDto = tokenDto;
    }
}
