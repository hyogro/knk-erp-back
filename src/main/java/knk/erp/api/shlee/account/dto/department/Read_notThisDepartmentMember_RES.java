package knk.erp.api.shlee.account.dto.department;

import lombok.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_notThisDepartmentMember_RES {
    private String code;
    private String message;
    private HashMap<String, String> dep_member;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RNDM001", "부서원 제외 직원 리스트 보기 성공");
        this.help_codeList.put("RNDM002", "부서원 제외 직원 리스트 보기 실패");
    }

    //error
    public Read_notThisDepartmentMember_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public Read_notThisDepartmentMember_RES(String code, HashMap<String, String> dep_member) {
        createCodeList();
        this.code = code;
        this.dep_member = dep_member;
    }
}
