package knk.erp.api.shlee.schedule.dto.Schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_createSchedule {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CS001", "일정생성 성공");
        this.help_codeList.put("CS002", "일정생성 실패");
    }

    //error
    public RES_createSchedule(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_createSchedule(String code) {
        createCodeList();
        this.code = code;
    }
}
