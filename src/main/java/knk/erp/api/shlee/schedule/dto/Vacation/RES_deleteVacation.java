package knk.erp.api.shlee.schedule.dto.Vacation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_deleteVacation {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("DV001", "휴가삭제 성공");
        this.help_codeList.put("DV002", "휴가삭제 실패");
        this.help_codeList.put("DV003", "휴가삭제 실패(이미 승인된 휴가)");
        this.help_codeList.put("DV004", "휴가삭제 실패(삭제권한 없음_본인만 삭제가능)");
    }

    //error
    public RES_deleteVacation(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_deleteVacation(String code) {
        createCodeList();
        this.code = code;
    }
}
