package knk.erp.api.shlee.schedule.dto.Schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_deleteSchedule {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("DS001", "일정삭제 성공");
        this.help_codeList.put("DS002", "일정삭제 실패");
    }

    //error
    public RES_deleteSchedule(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_deleteSchedule(String code) {
        createCodeList();
        this.code = code;
    }
}
