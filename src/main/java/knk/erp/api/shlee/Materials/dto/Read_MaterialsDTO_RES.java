package knk.erp.api.shlee.Materials.dto;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_MaterialsDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private List<String> materials;

    private void createCodeList(){
        this.help_codeList.put("RMTR001", "장기자재 사진 읽기 성공");
        this.help_codeList.put("RMTR002", "장기자재 사진 읽기 실패");
    }

    // error
    public Read_MaterialsDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Read_MaterialsDTO_RES(String code, List<String> materials) {
        createCodeList();
        this.code = code;
        this.materials = materials;
    }
}
