package knk.erp.api.shlee.board.entity;

import knk.erp.api.shlee.schedule.entity.Time;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "Board")
@Entity
public class Board extends Time {
    @Id
    @GeneratedValue
    private Long idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String target_department;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column
    private String reference_memberId;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column(nullable = false)
    private String memberId;

    @Column
    private Long dep_id;

    @Builder
    public Board(String title, String target_department, String content, String reference_memberId, BoardType boardType, String memberId,
                 Long dep_id){
        this.title = title;
        this.target_department = target_department;
        this.content = content;
        this.reference_memberId = reference_memberId;
        this.boardType = boardType;
        this.memberId = memberId;
        this.dep_id = dep_id;
    }
}