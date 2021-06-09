package knk.erp.api.shlee.board.dto;

import knk.erp.api.shlee.board.entity.Board;
import knk.erp.api.shlee.board.entity.BoardType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO_REQ {
    private Long idx;
    private String title;
    //private String target_department;
    private String content;
    private List<String> reference_memberName;  // 참조 대상 member 이름
    private String boardType;

    public Board toBoard(){
        return Board.builder()
                .title(title)
                //.target_department(target_department)
                .content(content)
                .reference_memberName(reference_memberName)
                .boardType(toBoardType(boardType))
                .build();
    }

    public BoardType toBoardType(String boardType){
        return boardType.equals("공지사항") ? BoardType.notice : BoardType.free;
    }
}
