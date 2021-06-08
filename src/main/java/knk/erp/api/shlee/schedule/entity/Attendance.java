package knk.erp.api.shlee.schedule.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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
public class Attendance extends Time {

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

    @Builder
    public Attendance(String memberId, Long departmentId, LocalDate attendanceDate, LocalTime onWork, LocalTime offWork) {
        this.memberId = memberId;
        this.departmentId = departmentId;
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
        this.offWork = offWork;
    }
}
