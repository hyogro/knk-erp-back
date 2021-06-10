package knk.erp.api.shlee.board.dto.boardlist;

import knk.erp.api.shlee.board.entity.Board;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardListSearchDTO_REQ {
    private String searchType;
    private String search_title;
    private String search_writer;
    private String search_tag;
}
