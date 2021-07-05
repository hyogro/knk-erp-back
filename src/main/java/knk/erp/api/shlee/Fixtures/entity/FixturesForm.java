package knk.erp.api.shlee.Fixtures.entity;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.Time;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixturesForm extends Time {

    @Id
    @GeneratedValue
    private Long id;

    //신청자
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    //승인자
    @ManyToOne
    @JoinColumn(name = "approver_id")
    private Member approver;

    //품목 리스트
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fixtures_form_id")
    private List<Fixtures> fixturesList;

    // 처리여부
    @Column
    private boolean check;

    @Builder
    public FixturesForm(Member author, Member approver, List<Fixtures> fixturesList, boolean check) {
        this.author = author;
        this.approver = approver;
        this.fixturesList = fixturesList;
        this.check = check;
    }
}
