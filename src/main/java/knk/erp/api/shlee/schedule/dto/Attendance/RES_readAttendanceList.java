package knk.erp.api.shlee.schedule.dto.Attendance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class RES_readAttendanceList {
    private String code;
    private String message;
    private List<AttendanceDTO> data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RAL001", "출근기록목록 조회 성공");
        this.help_codeList.put("RAL002", "출근기록목록 조회 실패");
    }
    //error
    public RES_readAttendanceList(String code) {
        createCodeList();
        this.code = code;
    }

    //error
    public RES_readAttendanceList(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readAttendanceList(String code, List<AttendanceDTO> data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
