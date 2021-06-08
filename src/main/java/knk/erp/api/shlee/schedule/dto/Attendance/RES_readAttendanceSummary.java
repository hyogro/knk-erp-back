package knk.erp.api.shlee.schedule.dto.Attendance;

import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_readAttendanceSummary {
    private String code;
    private String message;
    private AttendanceSummaryDTO data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RSS001", "일정요약 조회 성공");
        this.help_codeList.put("RSS002", "일정요약 조회 실패");
        this.help_codeList.put("RSS003", "일정요약 조회 실패(요약정보에 대한 권한 없음)");
    }

    //error
    public RES_readAttendanceSummary(String code) {
        createCodeList();
        this.code = code;
    }

    //error
    public RES_readAttendanceSummary(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readAttendanceSummary(String code, AttendanceSummaryDTO data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
