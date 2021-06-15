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

    @Column(length = 10, nullable = false)
    private String type;

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
    private String memberName;

    @Column(nullable = false)
    private Long departmentId;

    @Column(nullable = false)
    private String departmentName;

    @Builder
    public Vacation(String type, String memo, LocalDateTime startDate, LocalDateTime endDate, String memberId, String memberName, Long departmentId, String departmentName) {
        this.type = type;
        this.memo = memo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberId = memberId;
        this.memberName = memberName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }
}
