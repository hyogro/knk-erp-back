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
public class Read_BoardListDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private Page<Board> boardPage;

    private void createCodeList(){
        this.help_codeList.put("RBL001", "게시글 리스트 읽기 성공");
        this.help_codeList.put("RBL002", "게시글 리스트 읽기 실패");
    }

    // error
    public Read_BoardListDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Read_BoardListDTO_RES(String code, Page<Board> boardPage) {
        createCodeList();
        this.code = code;
        this.boardPage = boardPage;
    }
}
