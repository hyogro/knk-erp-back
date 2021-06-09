package knk.erp.api.shlee.board.dto.board;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_BoardDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("UB001", "게시글 수정 성공");
        this.help_codeList.put("UB002", "게시글 수정 실패");
        this.help_codeList.put("UB003", "게시글 수정 실패 - 게시글 작성자가 아님");
        this.help_codeList.put("UB004", "게시글 수정 실패 - 권한 부족");
    }

    // error
    public Update_BoardDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Update_BoardDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
