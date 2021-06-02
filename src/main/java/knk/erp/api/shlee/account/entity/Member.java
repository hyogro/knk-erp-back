package knk.erp.api.shlee.account.entity;

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
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String memberId;

    private String password;

    private Long phone;

    private String memberName;

    private float vacation;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder
    public Member(String memberId, Long phone, String memberName, String password, float vacation, Authority authority){
        this.memberId = memberId;
        this.phone = phone;
        this.memberName = memberName;
        this.password = password;
        this.vacation = vacation;
        this.authority = authority;
    }
}
