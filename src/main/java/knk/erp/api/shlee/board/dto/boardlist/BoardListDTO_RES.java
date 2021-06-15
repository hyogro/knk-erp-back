package knk.erp.api.shlee.board.dto.boardlist;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO_RES {
    private String title;
    private String writerMemberId;
    private LocalDateTime createDate;
    private String boardType;
}
