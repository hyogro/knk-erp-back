package knk.erp.api.shlee.domain.board.dto.boardlist;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Read_BoardListDTO_RES {
    private Page<BoardListDTO> page;
    private int totalPage;

    public Read_BoardListDTO_RES(Page<BoardListDTO> page, int totalPage) {
        this.page = page;
        this.totalPage = totalPage;
    }
}
