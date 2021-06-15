package knk.erp.api.shlee.schedule.responseEntity.vacation;

import knk.erp.api.shlee.schedule.dto.Attendance.AttendanceSummaryDTO;
import knk.erp.api.shlee.schedule.dto.Vacation.VacationSummaryDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_readVacationSummary {
    private String code;
    private String message;
    private VacationSummaryDTO data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RVS001", "휴가요약 조회 성공");
        this.help_codeList.put("RVS002", "휴가요약 조회 실패");
        this.help_codeList.put("RVS003", "휴가요약 조회 실패(요약정보에 대한 권한 없음)");
    }

    //error
    public RES_readVacationSummary(String code) {
        createCodeList();
        this.code = code;
    }

    //error
    public RES_readVacationSummary(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readVacationSummary(String code, VacationSummaryDTO data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
