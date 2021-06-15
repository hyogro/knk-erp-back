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
    private String title;
    private String content;
    private List<String> referenceMemberId;  // 참조 대상 memberId
    private String boardType;
    private String writerMemberId;
    private Long writerDepId;
    private List<String> fileName;
    private String writerMemberName;

    public Board toBoard(){
        return Board.builder()
                .title(title)
                .content(content)
                .referenceMemberId(referenceMemberId)
                .boardType(boardType)
                .writerMemberId(writerMemberId)
                .writerDepId(writerDepId)
                .writerMemberName(writerMemberName)
                .count(0)
                .build();
    }
}
