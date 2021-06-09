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
    boolean approval1;

    @Column
    boolean approval2;

    @Column(length = 30)
    private String approver1;

    @Column(length = 30)
    private String approver2;

    @Column
    boolean reject;

    @Column(length = 100)
    private String rejectMemo;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private Long departmentId;

    @Builder
    public Vacation(String title, String memo, boolean approval1, boolean approval2, String rejectMemo, LocalDateTime startDate, LocalDateTime endDate, String memberId, Long departmentId) {
        this.title = title;
        this.memo = memo;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.rejectMemo = rejectMemo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberId = memberId;
        this.departmentId = departmentId;
    }
}
