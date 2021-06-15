package knk.erp.api.shlee.account.dto.account;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_AccountDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    List<String> memberName;

    private void createCodeList(){
        this.help_codeList.put("RA001", "회원정보 목록 읽어오기 성공");
        this.help_codeList.put("RA002", "회원정보 목록 읽어오기 실패");
    }

    //error
    public Read_AccountDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Read_AccountDTO_RES(String code, List<String> memberName) {
        createCodeList();
        this.code = code;
        this.memberName = memberName;
    }
}
