package knk.erp.api.shlee.Fixtures.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadAll_FixturesFormDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private Page<ReadAll_FixturesFormDTO> page;
    private int pageSize;

    private void createCodeList(){
        this.help_codeList.put("RAFF001", "전체 비품 요청 목록 읽기 성공");
        this.help_codeList.put("RAFF002", "전체 비품 요청 목록 읽기 실패");
    }

    // error
    public ReadAll_FixturesFormDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public ReadAll_FixturesFormDTO_RES(String code, Page<ReadAll_FixturesFormDTO> page, int pageSize) {
        createCodeList();
        this.code = code;
        this.page = page;
        this.pageSize = pageSize;
    }
}
