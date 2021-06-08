package knk.erp.api.shlee.account.dto.my;

import knk.erp.api.shlee.account.dto.member.Read_MemberDTO;
import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSelf_MyPageDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("USM001", "본인 정보 수정 성공");
        this.help_codeList.put("USM002", "본인 정보 수정 실패");
    }

    // error
    public UpdateSelf_MyPageDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public UpdateSelf_MyPageDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
