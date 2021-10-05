package knk.erp.api.shlee.domain.Evaluation.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_EvaluationDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private String evaluation;

    private void createCodeList(){
        this.help_codeList.put("REV001", "평가표 읽기 성공");
        this.help_codeList.put("REV002", "평가표 읽기 실패");
    }

    // error
    public Read_EvaluationDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Read_EvaluationDTO_RES(String code, String message, String evaluation) {
        createCodeList();
        this.code = code;
        this.message = message;
        this.evaluation = evaluation;
    }
}
