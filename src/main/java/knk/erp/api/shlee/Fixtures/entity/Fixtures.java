package knk.erp.api.shlee.Fixtures.entity;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.Time;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fixtures extends Time {

    @Id
    @GeneratedValue
    private Long id;

    //품명
    @Column(length = 50)
    private String fixturesName;

    //갯수
    @Column
    private int amount;

    //승인 여부
    @Column
    private boolean confirm;

    //구매 여부
    @Column
    private boolean purchase;


    @Builder
    public Fixtures(String fixturesName, int amount, boolean confirm, boolean purchase) {
        this.fixturesName = fixturesName;
        this.amount = amount;
        this.confirm = confirm;
        this.purchase = purchase;
    }
}
