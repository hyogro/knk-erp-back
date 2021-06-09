package knk.erp.api.shlee.schedule.dto.Vacation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_rejectVacation {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RV001", "휴가거절 성공");
        this.help_codeList.put("RV002", "휴가거절 실패");
        this.help_codeList.put("RV003", "휴가거절 실패(권한 없음)");
        this.help_codeList.put("RV004", "휴가거절 실패(이미 승인된 휴가임)");
    }

    //error
    public RES_rejectVacation(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_rejectVacation(String code) {
        createCodeList();
        this.code = code;
    }
}
