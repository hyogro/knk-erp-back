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
public class Schedule extends Time {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column
    private String memo;

    @Column(length = 10)
    private String viewOption="all";

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    @Builder
    public Schedule(String title, String memo, String viewOption, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.memo = memo;
        this.viewOption = viewOption;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
