package knk.erp.api.shlee.board.entity;

import knk.erp.api.shlee.board.util.StringListConverter;
import knk.erp.api.shlee.schedule.entity.Time;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "Board")
@Entity
public class Board extends Time {
    @Id
    @GeneratedValue
    private Long idx;


    // 게시글 제목
    @Column(nullable = false)
    private String title;

    /*
    // 게시글 타겟 부서
    @Column(nullable = false)
    private String target_department;
    */

    // 게시글 내용
    @Column(nullable = false, length = 2000)
    private String content;

    // 참조 대상 memberName
    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> referenceMemberId;

    // 태그(ex : 공지사항, 자유게시판...)
    @Column(nullable = false)
    private String boardType;

    // 작성자 memberId
    @Column
    private String writerMemberId;

    // 작성자 부서 id
    @Column
    private Long writerDepId;

    @Builder
    public Board(String title, String content, List<String> referenceMemberId, String boardType, String writerMemberId,
                 Long writerDepId){
        this.title = title;
        //this.target_department = target_department;
        this.content = content;
        this.referenceMemberId = referenceMemberId;
        this.boardType = boardType;
        this.writerMemberId = writerMemberId;
        this.writerDepId = writerDepId;
    }
}