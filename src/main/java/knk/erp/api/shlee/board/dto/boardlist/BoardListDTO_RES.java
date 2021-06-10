package knk.erp.api.shlee.board.dto.boardlist;

import knk.erp.api.shlee.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO_RES {
    String title;
    String content;
    String writerMemberId;
    Long writerDepId;
    LocalDateTime createDate;
    LocalDateTime updateDate;
    List<String> referenceMemberId;



}
