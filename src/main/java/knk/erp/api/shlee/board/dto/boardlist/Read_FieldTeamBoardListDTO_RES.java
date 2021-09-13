package knk.erp.api.shlee.board.dto.boardlist;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_FieldTeamBoardListDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private Page<BoardListDTO> page;
    private int totalPage;

    private void createCodeList(){
        this.help_codeList.put("RFTB001", "현장팀게시글 목록 불러오기 성공");
        this.help_codeList.put("RFTB002", "현장팀게시글 목록 불러오기 실패");
    }

    // error
    public Read_FieldTeamBoardListDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Read_FieldTeamBoardListDTO_RES(String code, Page<BoardListDTO> page, int totalPage) {
        createCodeList();
        this.code = code;
        this.page = page;
        this.totalPage = totalPage;
    }
}
