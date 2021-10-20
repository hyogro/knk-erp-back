package knk.erp.api.shlee.domain.account.dto.my;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetMyVacation_MyPageDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private int vacation;

    private void createCodeList(){
        this.help_codeList.put("GMV001", "본인 잔여 연차 일수 보기 성공");
        this.help_codeList.put("GMV002", "본인 잔여 연차 일수 보기 실패");
    }

    // error
    public GetMyVacation_MyPageDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public GetMyVacation_MyPageDTO_RES(String code, int vacation) {
        createCodeList();
        this.code = code;
        this.vacation = vacation;
    }
}