package knk.erp.api.shlee.schedule.dto.Schedule;

import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class RES_readScheduleList {
    private String code;
    private String message;
    private List<ScheduleDTO> data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RSL001", "일정목록 조회 성공");
        this.help_codeList.put("RSL002", "일정목록조회 실패");
    }

    //error
    public RES_readScheduleList(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readScheduleList(String code, List<ScheduleDTO> data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
