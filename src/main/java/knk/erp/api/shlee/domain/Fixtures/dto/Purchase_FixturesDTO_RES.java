package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Purchase_FixturesDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("PFT001", "비품 구매 여부 변경 성공");
        this.help_codeList.put("PFT002", "비품 구매 여부 변경 실패");
    }

    // error
    public Purchase_FixturesDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Purchase_FixturesDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
