package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_FixturesFormDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("UFF001", "비품 요청서 수정 성공");
        this.help_codeList.put("UFF002", "비품 요청서 수정 실패");
        this.help_codeList.put("UFF003", "비품 요청서 수정 실패 - 비품 요청서 작성자가 아님");
        this.help_codeList.put("UFF004", "비품 요청서 수정 실패 - 이미 처리된 비품 요청서");
    }

    // error
    public Update_FixturesFormDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Update_FixturesFormDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
