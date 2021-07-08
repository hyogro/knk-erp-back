package knk.erp.api.shlee.schedule.entity;

import knk.erp.api.shlee.account.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddVacation extends Time {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private boolean increase;

    @Column
    private int date;

    @Column(length = 100)
    private String memo;


    @ManyToOne
    @JoinColumn(name = "giver_id")
    private Member giver;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Builder
    public AddVacation(boolean increase, int date, String memo) {
        this.increase = increase;
        this.date = date;
        this.memo = memo;
    }
}
