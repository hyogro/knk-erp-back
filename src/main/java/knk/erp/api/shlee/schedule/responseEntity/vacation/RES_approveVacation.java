package knk.erp.api.shlee.schedule.responseEntity.vacation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_approveVacation {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("AV001", "휴가승인 성공");
        this.help_codeList.put("AV002", "휴가승인 실패");
        this.help_codeList.put("AV003", "휴가승인 실패(권한 없음)");
    }

    //error
    public RES_approveVacation(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_approveVacation(String code) {
        createCodeList();
        this.code = code;
    }
}
