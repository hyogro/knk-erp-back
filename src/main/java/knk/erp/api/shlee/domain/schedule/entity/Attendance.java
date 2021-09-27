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
@SequenceGenerator(
        name = "attendance_id_generator",
        sequenceName = "atte_id_seq"
)
public class Attendance extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendance_id_generator")
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
    private String uuid;

    @Builder
    public Attendance(Member author, LocalDate attendanceDate, LocalTime onWork, LocalTime offWork, String uuid) {
        this.author = author;
        this.attendanceDate = attendanceDate;
        this.onWork = onWork;
        this.offWork = offWork;
        this.uuid = uuid;
    }

    public void doOnWork(){
        this.attendanceDate = LocalDate.now();
        this.onWork = LocalTime.now();
    }

    public void doOffWork(){
        this.offWork = LocalTime.now();
    }

}
