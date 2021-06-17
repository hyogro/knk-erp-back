package knk.erp.api.shlee.board.dto.boardlist;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_WorkBoardListDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private Page<BoardListDTO> page;

    private void createCodeList(){
        this.help_codeList.put("RWB001", "업무게시글 목록 불러오기 성공");
        this.help_codeList.put("RWB002", "업무게시글 목록 불러오기 실패");
    }

    // error
    public Read_WorkBoardListDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Read_WorkBoardListDTO_RES(String code, Page<BoardListDTO> page) {
        createCodeList();
        this.code = code;
        this.page = page;
    }
}
