package knk.erp.api.shlee.board.dto.boardlist;

import knk.erp.api.shlee.board.entity.Board;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Search_BoardListDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private Page<BoardListDTO_RES> page;

    private void createCodeList(){
        this.help_codeList.put("SBL001", "게시글 리스트 읽기 성공");
        this.help_codeList.put("SBL002", "게시글 리스트 읽기 실패");
    }

    // error
    public Search_BoardListDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Search_BoardListDTO_RES(String code, Page<BoardListDTO_RES> page) {
        createCodeList();
        this.code = code;
        this.page = page;
    }
}
