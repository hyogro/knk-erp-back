package knk.erp.api.shlee.schedule.responseEntity.attendance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_deleteRectifyAttendance {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("DRA001", "퇴근기록 정정요청 삭제 성공");
        this.help_codeList.put("DRA002", "퇴근기록 정정요청 삭제 실패");
        this.help_codeList.put("DRA003", "퇴근기록 정정요청 삭제 실패(권한 없음)");
    }

    //error
    public RES_deleteRectifyAttendance(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_deleteRectifyAttendance(String code) {
        createCodeList();
        this.code = code;
    }
}
