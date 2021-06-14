package knk.erp.api.shlee.board.dto.boardlist;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoticeListDTO_RES {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();
    private Page<BoardListDTO_RES> page;

    private void createCodeList(){
        this.help_codeList.put("NBL001", "공지사항 최신순 5개 불러오기 성공");
        this.help_codeList.put("NBL002", "공지사항 최신순 5개 불러오기 실패");
    }

    // error
    public NoticeListDTO_RES(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    // ok
    public NoticeListDTO_RES(String code, Page<BoardListDTO_RES> page) {
        createCodeList();
        this.code = code;
        this.page = page;
    }
}
