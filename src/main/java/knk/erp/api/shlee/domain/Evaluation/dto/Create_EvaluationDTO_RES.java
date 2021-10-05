package knk.erp.api.shlee.domain.Evaluation.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Create_EvaluationDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CEV001", "평가표 업로드 성공");
        this.help_codeList.put("CEV002", "평가표 업로드 실패");
    }

    // error
    public Create_EvaluationDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Create_EvaluationDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
