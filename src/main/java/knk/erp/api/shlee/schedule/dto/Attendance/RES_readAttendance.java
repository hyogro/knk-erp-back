package knk.erp.api.shlee.schedule.dto.Attendance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class RES_readAttendance {
    private String code;
    private String message;
    private AttendanceDTO data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RA001", "출근기록 조회 성공");
        this.help_codeList.put("RA002", "출근기록 조회 실패");
        this.help_codeList.put("RA003", "출근기록 조회 실패(출근정보 존재하지 않음)");
    }
    //error
    public RES_readAttendance(String code) {
        createCodeList();
        this.code = code;
    }

    //error
    public RES_readAttendance(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readAttendance(String code, AttendanceDTO data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
