package knk.erp.api.shlee.schedule.dto.Attendance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_updateRectifyAttendance {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("URA001", "퇴근기록 정정요청 성공");
        this.help_codeList.put("URA002", "퇴근기록 정정요청 실패");
        this.help_codeList.put("URA003", "퇴근기록 정정요청 실패(권한없음)");
    }

    //error
    public RES_updateRectifyAttendance(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_updateRectifyAttendance(String code) {
        createCodeList();
        this.code = code;
    }
}
