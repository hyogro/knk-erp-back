package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Confrim_FixturesDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CFT001", "비품 승인 및 거절 성공");
        this.help_codeList.put("CFT002", "비품 승인 및 거절 실패");
    }

    // error
    public Confrim_FixturesDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Confrim_FixturesDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
