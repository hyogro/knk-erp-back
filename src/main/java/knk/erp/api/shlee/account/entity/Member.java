package knk.erp.api.shlee.account.entity;

import knk.erp.api.shlee.schedule.entity.Time;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "Member")
@Entity
public class Member extends Time {
    @Id
    @GeneratedValue
    private Long id;

    //2021-07-05 김효준 추가
    @Column(length = 5)
    private String position;

    //2021-06-25 이상훈 추가 unique = true
    @Column(nullable = false, length = 20, unique = true)
    private String memberId;

    @Column(nullable = false, length = 70)
    private String password;

    @Column(length = 13, nullable = false)
    private String phone;

    @Column(nullable = false, length = 10)
    private String memberName;

    @Column
    private LocalDate joiningDate;

    @Column(length = 150)
    private String address;

    @Column(length = 40)
    private String email;

    @Column
    private int vacation;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(length = 50)
    private String images;

    @Column(length = 50)
    private LocalDate birthDate;

    @Column
    private boolean birthDateSolar = true;

    @Builder
    public Member(String memberId, String phone, String memberName, String password, int vacation, Authority authority,
                  LocalDate joiningDate, String address, String email, String images, LocalDate birthDate, boolean birthDateSolar,
                  String position){
        this.memberId = memberId;
        this.phone = phone;
        this.memberName = memberName;
        this.password = password;
        this.vacation = vacation;
        this.authority = authority;
        this.joiningDate = joiningDate;
        this.address = address;
        this.email = email;
        this.images = images;
        this.birthDate = birthDate;
        this.birthDateSolar = birthDateSolar;
        this.position = position;
    }
}
