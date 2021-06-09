package knk.erp.api.shlee.schedule.dto.Vacation;

import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class RES_readVacationList {
    private String code;
    private String message;
    private List<VacationDTO> data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RVL001", "휴가목록 조회 성공");
        this.help_codeList.put("RVL002", "휴가목록 조회 실패");
    }

    //error
    public RES_readVacationList(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readVacationList(String code, List<VacationDTO> data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
