package knk.erp.api.shlee.board.dto.board;

import knk.erp.api.shlee.board.entity.Board;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long idx;
    private String title;
    //private String target_department;
    private String content;
    private List<String> referenceMemberName;  // 참조 대상 member 이름
    private String boardType;
    private String writerMemberId;
    private Long writerDepId;

    public Board toBoard(){
        return Board.builder()
                .title(title)
                //.target_department(target_department)
                .content(content)
                .referenceMemberName(referenceMemberName)
                .boardType(boardType)
                .writerMemberId(writerMemberId)
                .writerDepId(writerDepId)
                .build();
    }
}
