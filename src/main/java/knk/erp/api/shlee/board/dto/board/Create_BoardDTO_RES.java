package knk.erp.api.shlee.board.dto.board;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Create_BoardDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("CB001", "게시글 생성 성공");
        this.help_codeList.put("CB002", "게시글 생성 실패");
        this.help_codeList.put("CB003", "게시글 생성 실패 - 권한 부족");
        this.help_codeList.put("CB004", "게시글 생성 실패 - 작성자와 다른 부서 태그");
    }

    // error
    public Create_BoardDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Create_BoardDTO_RES(String code) {
        createCodeList();
        this.code = code;
    }
}
