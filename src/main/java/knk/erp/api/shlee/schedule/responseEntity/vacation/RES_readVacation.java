package knk.erp.api.shlee.schedule.responseEntity.vacation;

import knk.erp.api.shlee.schedule.dto.Vacation.VacationDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class RES_readVacation {
    private String code;
    private String message;
    private VacationDTO data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RV001", "휴가목록 조회 성공");
        this.help_codeList.put("RV002", "휴가목록 조회 실패");
        this.help_codeList.put("RV003", "휴가목록 조회 실패(권한없음)");
    }

    //error
    public RES_readVacation(String code) {
        createCodeList();
        this.code = code;
    }//error
    public RES_readVacation(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readVacation(String code, VacationDTO data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
