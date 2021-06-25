package knk.erp.api.shlee.board.entity;

import knk.erp.api.shlee.board.util.StringListConverter;
import knk.erp.api.shlee.file.entity.File;
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
@SequenceGenerator(
        name = "board_idx_generator",
        sequenceName = "board_idx"
)
public class Board extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_idx_generator")
    private Long idx;

    // 게시글 제목
    @Column(nullable = false, length = 80)
    private String title;

    // 게시글 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // 참조 대상 memberId
    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> referenceMemberId;

    // 태그(ex : 공지사항, 자유게시판...)
    @Column(nullable = false, length = 20)
    private String boardType;

    // 작성자 memberId
    @Column(length = 20)
    private String writerMemberId;

    // 작성자 이름
    @Column(length = 10)
    private String writerMemberName;

    // 작성자 부서 id
    @Column
    private Long writerDepId;

    @Column
    private int count;

    @Column(length = 1000)
    @Convert(converter = StringListConverter.class)
    private List<String> visitors;

    @OneToMany
    @JoinColumn(name = "board_idx")
    private List<File> file;

    @Builder
    public Board(String title, String content, List<String> referenceMemberId, String boardType, String writerMemberId,
                 Long writerDepId, String writerMemberName, int count){
        this.title = title;
        this.content = content;
        this.referenceMemberId = referenceMemberId;
        this.boardType = boardType;
        this.writerMemberId = writerMemberId;
        this.writerDepId = writerDepId;
        this.writerMemberName = writerMemberName;
        this.count = count;
    }
}