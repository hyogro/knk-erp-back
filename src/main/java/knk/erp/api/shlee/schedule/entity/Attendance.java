package knk.erp.api.shlee.schedule.entity;

import knk.erp.api.shlee.account.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends Time {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    @Column(nullable = false)
    private Long departmentId;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private LocalTime onWork;

    @Column
    private LocalTime offWork;

    @Builder
    public Attendance(Long departmentId, LocalDate attendanceDate, LocalTime onWork, LocalTime offWork) {
        this.departmentId = departmentId;
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
        this.offWork = offWork;
    }
}
