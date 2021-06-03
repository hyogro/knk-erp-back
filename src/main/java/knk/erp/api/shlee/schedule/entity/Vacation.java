package knk.erp.api.shlee.schedule.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @Column(length = 50, nullable = false)
    private String title;

    @Column
    private String memo;

    @Column
    private boolean lvl2Approve;

    @Column
    private boolean lvl3Approve;

    @Column(length = 100)
    private String approveMemo;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long groupId;

    @Builder
    public Vacation(String title, String memo, boolean lvl2Approve, boolean lvl3Approve, String approveMemo, LocalDateTime startDate, LocalDateTime endDate, Long memberId, Long groupId) {
        this.title = title;
        this.memo = memo;
        this.lvl2Approve = lvl2Approve;
        this.lvl3Approve = lvl3Approve;
        this.approveMemo = approveMemo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberId = memberId;
        this.groupId = groupId;
    }
}
