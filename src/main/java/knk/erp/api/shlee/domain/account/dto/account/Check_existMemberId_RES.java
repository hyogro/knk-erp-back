package knk.erp.api.shlee.domain.account.dto.account;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Check_existMemberId_RES {
    private String code;
    private String message;
    private boolean check;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CMI001", "중복 ID 체크 성공 - true : 중복O, false : 중복X");
        this.help_codeList.put("CMI002", "중복 ID 체크 실패");
    }

    //error
    public Check_existMemberId_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Check_existMemberId_RES(String code, boolean check) {
        createCodeList();
        this.code = code;
        this.check = check;
    }
}
