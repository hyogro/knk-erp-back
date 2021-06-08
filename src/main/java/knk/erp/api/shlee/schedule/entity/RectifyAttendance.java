package knk.erp.api.shlee.schedule.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @Column(nullable = false, length = 30)
    private String memberId;

    @Column(nullable = false)
    private Long departmentId;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private LocalTime onWork;

    @Column
    private LocalTime offWork;

    @Column
    private boolean approval_1;

    @Column
    private boolean approval_2;

    @Column(length = 30)
    private String approver_1;

    @Column(length = 30)
    private String approver_2;

    @Column(length = 100)
    private String memo;

    @Column(unique = true)
    private Long targetId;

    @Builder
    public RectifyAttendance(String memberId, Long departmentId, LocalDate attendanceDate, LocalTime onWork, LocalTime offWork, String memo, Long targetId) {
        this.memberId = memberId;
        this.departmentId = departmentId;
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
        this.offWork = offWork;
        this.memo = memo;
        this.targetId = targetId;
    }
}
