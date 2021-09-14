package knk.erp.api.shlee.domain.schedule.entity;

import knk.erp.api.shlee.domain.account.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RectifyAttendance extends Time {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private LocalTime onWork;

    @Column
    private LocalTime offWork;

    @Column
    private boolean approval1;

    @Column
    private boolean approval2;

    @ManyToOne
    @JoinColumn(name = "approver_1_id")
    private Member approver1;

    @ManyToOne
    @JoinColumn(name = "approver_2_id")
    private Member approver2;

    @Column(length = 100)
    private String memo;

    @Column(unique = true)
    private Long targetId;

    @Builder
    public RectifyAttendance(LocalDate attendanceDate, LocalTime onWork, LocalTime offWork, String memo, Long targetId) {
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
        this.offWork = offWork;
        this.memo = memo;
        this.targetId = targetId;
    }
}
