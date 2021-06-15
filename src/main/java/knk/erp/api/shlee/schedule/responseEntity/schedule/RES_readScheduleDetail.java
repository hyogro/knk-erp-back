package knk.erp.api.shlee.schedule.responseEntity.schedule;

import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
public class RES_readScheduleDetail {
    private String code;
    private String message;
    private ScheduleDTO data;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("RSD001", "일정상세 조회 성공");
        this.help_codeList.put("RSD002", "일정상세 조회 실패");
    }

    //error
    public RES_readScheduleDetail(String code) {
        createCodeList();
        this.code = code;
    }

    //error
    public RES_readScheduleDetail(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_readScheduleDetail(String code, ScheduleDTO data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
