package knk.erp.api.shlee.domain.board.dto.boardlist;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO {
    private Long board_idx;
    private String title;
    private String writerMemberName;
    private LocalDateTime createDate;
    private String boardType;
    private List<String> visitors;
}
