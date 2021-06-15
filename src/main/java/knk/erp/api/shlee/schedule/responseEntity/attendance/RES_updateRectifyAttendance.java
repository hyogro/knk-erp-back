package knk.erp.api.shlee.schedule.responseEntity.attendance;

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
        this.help_codeList.put("URA001", "출,퇴근기록 정정요청 성공");
        this.help_codeList.put("URA002", "출,퇴근기록 정정요청 실패");
        this.help_codeList.put("URA003", "출,퇴근기록 정정요청 실패(권한없음)");
        this.help_codeList.put("URA004", "출,퇴근기록 정정요청 실패(이미 정정요청 되었거나 삭제된 기록임)");
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
