package knk.erp.api.shlee.schedule.responseEntity.attendance;

import knk.erp.api.shlee.schedule.dto.Attendance.RectifyAttendanceDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class RES_readRectifyAttendance {
    private String code;
    private String message;
    private RectifyAttendanceDTO data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RRA001", "출,퇴근 정정요청 조회 성공");
        this.help_codeList.put("RRA002", "출,퇴근 정정요청 조회 실패");
        this.help_codeList.put("RRA003", "출,퇴근 정정요청 조회 실패(권한 없음)");
    }
    //error
    public RES_readRectifyAttendance(String code) {
        createCodeList();
        this.code = code;
    }

    //error
    public RES_readRectifyAttendance(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readRectifyAttendance(String code, RectifyAttendanceDTO data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
