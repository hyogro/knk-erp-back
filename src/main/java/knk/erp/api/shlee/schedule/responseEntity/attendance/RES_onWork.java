package knk.erp.api.shlee.schedule.responseEntity.attendance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_onWork {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("ON001", "출근기록 성공");
        this.help_codeList.put("ON002", "출근기록 실패");
        this.help_codeList.put("ON003", "출근기록 실패(이미 출근기록 있음)");
        this.help_codeList.put("ON004", "출근기록 실패(권한 없음)");
    }

    //error
    public RES_onWork(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_onWork(String code) {
        createCodeList();
        this.code = code;
    }
}
