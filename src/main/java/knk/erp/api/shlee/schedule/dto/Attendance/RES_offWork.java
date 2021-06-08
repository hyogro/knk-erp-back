package knk.erp.api.shlee.schedule.dto.Attendance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_offWork {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("OFF001", "퇴근기록 성공");
        this.help_codeList.put("OFF002", "퇴근기록 실패");
        this.help_codeList.put("OFF003", "퇴근기록 실패(기존 퇴근기록 존재)");
        this.help_codeList.put("OFF004", "퇴근기록 실패(권한 없음)");
        this.help_codeList.put("OFF005", "퇴근기록 실패(출근기록 존재하지 않음)");
    }

    //error
    public RES_offWork(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_offWork(String code) {
        createCodeList();
        this.code = code;
    }
}
