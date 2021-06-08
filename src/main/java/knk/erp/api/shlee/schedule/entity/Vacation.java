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
    boolean approval_1;

    @Column
    boolean approval_2;

    @Column(length = 100)
    private String approveMemo;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long departmentId;

    @Builder
    public Vacation(String title, String memo, boolean approval_1, boolean approval_2, String approveMemo, LocalDateTime startDate, LocalDateTime endDate, Long memberId, Long departmentId) {
        this.title = title;
        this.memo = memo;
        this.approval_1 = approval_1;
        this.approval_2 = approval_2;
        this.approveMemo = approveMemo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberId = memberId;
        this.departmentId = departmentId;
    }
}
