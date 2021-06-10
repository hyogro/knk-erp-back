package knk.erp.api.shlee.board.dto.boardlist;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardListSearchDTO_REQ {
    private String searchType;
    private String keyword;
}
