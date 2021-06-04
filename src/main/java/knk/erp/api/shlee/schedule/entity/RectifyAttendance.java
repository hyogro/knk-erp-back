package knk.erp.api.shlee.schedule.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private LocalDateTime onWork;

    @Column
    private LocalDateTime offWork;

    @Column
    private boolean approval_1;

    @Column
    private boolean approval_2;

    @Column(length = 100)
    private String memo;




    @Builder
    public RectifyAttendance(String memberId, LocalDate attendanceDate, LocalDateTime onWork, LocalDateTime offWork, String memo) {
        this.memberId = memberId;
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
        this.offWork = offWork;
        this.memo = memo;
    }
}
