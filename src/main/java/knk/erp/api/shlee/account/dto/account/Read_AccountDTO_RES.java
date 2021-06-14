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
public class Read_AccountDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    List<Read_MemberDTO> memberList;

    private void createCodeList(){
        this.help_codeList.put("RA001", "회원정보 목록 읽어오기 성공");
        this.help_codeList.put("RA002", "회원정보 목록 읽어오기 실패");
        this.help_codeList.put("RA003", "회원정보 목록 읽어오기 실패 - 입력한 부서가 없거나 부서에 멤버가 없음");
    }

    //error
    public Read_AccountDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Read_AccountDTO_RES(String code, List<Read_MemberDTO> memberList) {
        createCodeList();
        this.code = code;
        this.memberList = memberList;
    }
}
