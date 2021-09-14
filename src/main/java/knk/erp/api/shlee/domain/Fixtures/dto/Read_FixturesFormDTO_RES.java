package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_FixturesFormDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private List<Read_FixturesFormDTO> readFixturesFormDTO;

    private void createCodeList(){
        this.help_codeList.put("RFF001", "내 비품 요청 목록 읽기 성공");
        this.help_codeList.put("RFF002", "내 비품 요청 목록 읽기 실패");
    }

    // error
    public Read_FixturesFormDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Read_FixturesFormDTO_RES(String code, List<Read_FixturesFormDTO> readFixturesFormDTO) {
        createCodeList();
        this.code = code;
        this.readFixturesFormDTO = readFixturesFormDTO;
    }
}
