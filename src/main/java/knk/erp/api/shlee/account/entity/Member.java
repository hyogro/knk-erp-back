package knk.erp.api.shlee.account.entity;

import knk.erp.api.shlee.schedule.entity.Time;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "Member")
@Entity
public class Member extends Time {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String memberId;

    @Column
    private String password;

    @Column(length = 11)
    private String phone;

    @Column
    private String memberName;

    @Column
    private float vacation;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder
    public Member(String memberId, String phone, String memberName, String password, float vacation, Authority authority){
        this.memberId = memberId;
        this.phone = phone;
        this.memberName = memberName;
        this.password = password;
        this.vacation = vacation;
        this.authority = authority;
    }
}
