package knk.erp.api.shlee.schedule.dto.Schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_updateSchedule {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("US001", "일정수정 성공");
        this.help_codeList.put("US002", "일정수정 실패");
    }

    //error
    public RES_updateSchedule(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_updateSchedule(String code) {
        createCodeList();
        this.code = code;
    }
}
