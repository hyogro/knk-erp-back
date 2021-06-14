package knk.erp.api.shlee.account.dto.account;

import knk.erp.api.shlee.account.dto.member.Read_MemberDTO;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_AccountDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    ReadDetail_AccountDTO readDetailAccountDTO;

    private void createCodeList(){
        this.help_codeList.put("RDA001", "회원정보 상세보기 성공");
        this.help_codeList.put("RDA002", "회원정보 상세보기 실패");
    }

    //error
    public ReadDetail_AccountDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public ReadDetail_AccountDTO_RES(String code, ReadDetail_AccountDTO readDetailAccountDTO) {
        createCodeList();
        this.code = code;
        this.readDetailAccountDTO = readDetailAccountDTO;
    }
}
