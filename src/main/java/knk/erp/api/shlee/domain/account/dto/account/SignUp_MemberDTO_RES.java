package knk.erp.api.shlee.domain.account.dto.account;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignUp_MemberDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("SU001", "회원가입 성공");
        this.help_codeList.put("SU002", "회원가입 실패");
     }

    //error
    public SignUp_MemberDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public SignUp_MemberDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}