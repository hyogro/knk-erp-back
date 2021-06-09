package knk.erp.api.shlee.board.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_BoardDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private Read_BoardDTO readBoardDTO;

    private void createCodeList(){
        this.help_codeList.put("RB001", "게시글 읽기 성공");
        this.help_codeList.put("RB002", "게시글 읽기 실패");
        this.help_codeList.put("RB003", "게시글 읽기 실패 - 참조 대상이 아님");
    }

    // error
    public Read_BoardDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Read_BoardDTO_RES(String code, Read_BoardDTO readBoardDTO) {
        createCodeList();
        this.code = code;
        this.readBoardDTO = readBoardDTO;
    }
}
