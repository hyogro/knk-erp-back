package knk.erp.api.shlee.account.dto.my;

import knk.erp.api.shlee.account.dto.member.Read_MemberDTO;
import knk.erp.api.shlee.account.entity.Member;
import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetMyInfo_MyPageDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private Read_MemberDTO readMemberDTO;

    private void createCodeList(){
        this.help_codeList.put("GMI001", "본인 정보 보기 성공");
        this.help_codeList.put("GMI002", "본인 정보 보기 실패");
        this.help_codeList.put("GMI003", "본인 정보 보기 실패 - 로그인 정보 없음");
    }

    // error
    public GetMyInfo_MyPageDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public GetMyInfo_MyPageDTO_RES(String code, Read_MemberDTO readMemberDTO) {
        createCodeList();
        this.code = code;
        this.readMemberDTO = readMemberDTO;
    }
}
