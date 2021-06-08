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

    @Column(nullable = false, length = 30)
    private String memberId;

    @Column
    private Long departmentId;

    @Builder
    public Schedule(String title, String memo, String viewOption, LocalDateTime startDate, LocalDateTime endDate, String memberId, Long departmentId) {
        this.title = title;
        this.memo = memo;
        this.viewOption = viewOption;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberId = memberId;
        this.departmentId = departmentId;
    }
}
