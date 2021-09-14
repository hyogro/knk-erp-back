package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_FixturesFormDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private ReadDetail_FixturesFormDTO readDetailFixturesFormDTO;

    private void createCodeList(){
        this.help_codeList.put("RDFF001", "비품 요청서 상세보기 성공");
        this.help_codeList.put("RDFF002", "비품 요청서 상세보기 실패");
    }

    // error
    public ReadDetail_FixturesFormDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public ReadDetail_FixturesFormDTO_RES(String code, ReadDetail_FixturesFormDTO readDetailFixturesFormDTO) {
        createCodeList();
        this.code = code;
        this.readDetailFixturesFormDTO = readDetailFixturesFormDTO;
    }
}
