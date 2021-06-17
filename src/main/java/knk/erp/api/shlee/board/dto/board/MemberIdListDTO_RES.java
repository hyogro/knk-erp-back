package knk.erp.api.shlee.board.dto.board;

import lombok.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberIdListDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private HashMap<String, HashMap<String, String>> data;

    private void createCodeList(){
        this.help_codeList.put("MIL001", "멤버 id, 이름 목록 불러오기 성공");
        this.help_codeList.put("MIL002", "멤버 id, 이름 목록 불러오기 실패");
    }

    // error
    public MemberIdListDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public MemberIdListDTO_RES(String code, HashMap<String, HashMap<String, String>> data) {
        createCodeList();
        this.code = code;
        this.data = data;
    }
}
