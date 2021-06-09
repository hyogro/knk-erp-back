package knk.erp.api.shlee.board.dto;

import knk.erp.api.shlee.board.entity.Board;
import knk.erp.api.shlee.board.entity.BoardType;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO_REQ {
    private String title;
    private String target_department;
    private String content;
    private String reference_memberId;
    private String boardType;

    public Board toBoard(){
        return Board.builder()
                .title(title)
                .target_department(target_department)
                .content(content)
                .reference_memberId(reference_memberId)
                .boardType(toBoardType(boardType))
                .build();
    }

    public BoardType toBoardType(String boardType){
        return boardType.equals("공지사항") ? BoardType.notice : BoardType.free;
    }
}
