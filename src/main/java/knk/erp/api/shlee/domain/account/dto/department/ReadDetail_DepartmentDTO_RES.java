package knk.erp.api.shlee.domain.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_DepartmentDTO_RES {
    private String code;
    private String message;
    private ReadDetail_DepartmentDTO readDetailDepartmentDTO;
    private List<Read_DepartmentMemberListDTO> readDepartmentMemberListDTO;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RDD001", "부서 상세 보기 성공");
        this.help_codeList.put("RDD002", "부서 상세 보기 실패");
    }

    //error
    public ReadDetail_DepartmentDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public ReadDetail_DepartmentDTO_RES(String code, ReadDetail_DepartmentDTO readDetailDepartmentDTO,
                                        List<Read_DepartmentMemberListDTO> readDepartmentMemberListDTO) {
        createCodeList();
        this.code = code;
        this.readDetailDepartmentDTO = readDetailDepartmentDTO;
        this.readDepartmentMemberListDTO = readDepartmentMemberListDTO;
    }
}
