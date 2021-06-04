package knk.erp.api.shlee.schedule.dto.Attendance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_createRectifyAttendance {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CRA001", "출,퇴근 정정요청 성공");
        this.help_codeList.put("CRA002", "출,퇴근 정정요청 실패");
        this.help_codeList.put("CRA003", "출,퇴근 정정요청 실패(권한 없음)");
    }

    //error
    public RES_createRectifyAttendance(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_createRectifyAttendance(String code) {
        createCodeList();
        this.code = code;
    }
}
