package knk.erp.api.shlee.schedule.dto.Attendance;

import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class RES_readRectifyAttendanceList {
    private String code;
    private String message;
    private List<RectifyAttendanceDTO> data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RRAL001", "출,퇴근 정정요청목록 조회 성공");
        this.help_codeList.put("RRAL002", "출,퇴근 정정요청목록 조회 실패");
        this.help_codeList.put("RRAL003", "출,퇴근 정정요청목록 조회 실패(권한 없음)");
    }
    //error
    public RES_readRectifyAttendanceList(String code) {
        createCodeList();
        this.code = code;
    }

    //error
    public RES_readRectifyAttendanceList(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readRectifyAttendanceList(String code, List<RectifyAttendanceDTO> data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
