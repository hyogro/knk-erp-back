package knk.erp.api.shlee.schedule.dto.Attendance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_approveRectifyAttendance {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("ARA001", "출,퇴근기록 정정승인 성공");
        this.help_codeList.put("ARA002", "출,퇴근기록 정정승인 실패");
        this.help_codeList.put("ARA003", "출,퇴근기록 정정요청 실패(권한 없음)");
        this.help_codeList.put("ARA004", "출,퇴근기록 정정요청 실패(이미 승인, 삭제된 정정요청임)");
    }

    //error
    public RES_approveRectifyAttendance(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_approveRectifyAttendance(String code) {
        createCodeList();
        this.code = code;
    }
}
