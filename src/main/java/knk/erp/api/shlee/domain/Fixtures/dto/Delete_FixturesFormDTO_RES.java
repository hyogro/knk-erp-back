package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Delete_FixturesFormDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("DFF001", "비품 요청서 삭제 성공");
        this.help_codeList.put("DFF002", "비품 요청서 삭제 실패");
        this.help_codeList.put("DFF003", "비품 요청서 삭제 실패 - 비품 요청서 작성자가 아님");
        this.help_codeList.put("DFF004", "비품 요청서 삭제 실패 - 이미 처리된 비품 요청서");
    }

    // error
    public Delete_FixturesFormDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Delete_FixturesFormDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
