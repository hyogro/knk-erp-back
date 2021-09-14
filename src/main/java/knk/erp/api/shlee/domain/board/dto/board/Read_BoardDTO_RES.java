package knk.erp.api.shlee.domain.board.dto.board;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

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
    private List<Read_ReferenceMemberDTO> readReferenceMemberDTO;

    private void createCodeList(){
        this.help_codeList.put("RB001", "게시글 읽기 성공");
        this.help_codeList.put("RB002", "게시글 읽기 실패");
    }

    // error
    public Read_BoardDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public Read_BoardDTO_RES(String code, Read_BoardDTO readBoardDTO, List<Read_ReferenceMemberDTO> readReferenceMemberDTO) {
        createCodeList();
        this.code = code;
        this.readBoardDTO = readBoardDTO;
        this.readReferenceMemberDTO = readReferenceMemberDTO;
    }
}
