package knk.erp.api.shlee.domain.schedule.entity;

import knk.erp.api.shlee.domain.account.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vacation extends Time {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 10, nullable = false)
    private String type;

    @Column
    private String memo;

    @Column
    boolean approval1;

    @Column
    boolean approval2;

    @ManyToOne
    @JoinColumn(name = "approver_1_id")
    private Member approver1;

    @ManyToOne
    @JoinColumn(name = "approver_2_id")
    private Member approver2;

    @Column
    boolean reject;

    @Column(length = 100)
    private String rejectMemo;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    @Builder
    public Vacation(String type, String memo, LocalDateTime startDate, LocalDateTime endDate) {
        this.type = type;
        this.memo = memo;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
