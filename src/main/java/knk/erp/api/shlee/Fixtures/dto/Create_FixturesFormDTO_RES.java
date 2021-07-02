package knk.erp.api.shlee.Fixtures.dto;

import knk.erp.api.shlee.account.dto.member.Read_MemberDTO;
import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Create_FixturesFormDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CFF001", "비품 요청 생성 성공");
        this.help_codeList.put("CFF002", "비품 요청 생성 실패");
    }

    // error
    public Create_FixturesFormDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Create_FixturesFormDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
