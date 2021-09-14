package knk.erp.api.shlee.domain.Materials.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Create_MaterialsDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CMTR001", "장기자재 사진 업로드 성공");
        this.help_codeList.put("CMTR002", "장기자재 사진 업로드 실패");
        this.help_codeList.put("CMTR003", "장기자재 사진 업로드 실패 - 권한없음");
    }

    // error
    public Create_MaterialsDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Create_MaterialsDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
