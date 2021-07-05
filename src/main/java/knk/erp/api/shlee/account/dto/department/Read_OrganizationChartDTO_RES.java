package knk.erp.api.shlee.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_OrganizationChartDTO_RES {
    private String code;
    private String message;
    private List<OrganizationChartDTO> organizationChartDTO;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("ROC001", "조직도 보기 성공");
        this.help_codeList.put("ROC002", "조직도 보기 실패");
    }

    //error
    public Read_OrganizationChartDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Read_OrganizationChartDTO_RES(String code, List<OrganizationChartDTO> organizationChartDTO) {
        createCodeList();
        this.code = code;
        this.organizationChartDTO = organizationChartDTO;
    }
}
