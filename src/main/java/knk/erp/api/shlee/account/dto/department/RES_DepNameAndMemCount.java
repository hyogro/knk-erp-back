package knk.erp.api.shlee.account.dto.department;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_DepNameAndMemCount {
    private String code;
    private String message;
    private DepartmentNameAndMemberCountDTO data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RDAM001", "부서정보 조회 성공");
        this.help_codeList.put("RDAM002", "부서정보 조회 실패");
        this.help_codeList.put("RDAM003", "부서정보 조회 실패(해당직원이 속한 부서가 없음)");
    }

    public RES_DepNameAndMemCount(String code, DepartmentNameAndMemberCountDTO data){
        createCodeList();
        this.code = code;
        this.data = data;
    }
    public RES_DepNameAndMemCount(String code, String message){
        createCodeList();
        this.code = code;
        this.message = message;
    }

}
