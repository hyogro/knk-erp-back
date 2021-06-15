package knk.erp.api.shlee.schedule.responseEntity.vacation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_createVacation {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CV001", "휴가요청생성 성공");
        this.help_codeList.put("CV002", "휴가요청생성 실패");
    }

    //error
    public RES_createVacation(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_createVacation(String code) {
        createCodeList();
        this.code = code;
    }
}
