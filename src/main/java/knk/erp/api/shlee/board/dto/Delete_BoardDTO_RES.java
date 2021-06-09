package knk.erp.api.shlee.board.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Delete_BoardDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("DB001", "게시글 삭제 성공");
        this.help_codeList.put("DB002", "게시글 삭제 실패");
        this.help_codeList.put("DB003", "게시글 삭제 실패 - 게시글 작성자가 아님");
    }

    // error
    public Delete_BoardDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Delete_BoardDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
