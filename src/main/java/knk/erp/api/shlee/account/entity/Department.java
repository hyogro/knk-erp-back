package knk.erp.api.shlee.account.entity;

import knk.erp.api.shlee.schedule.entity.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table(name = "Department")
@NoArgsConstructor
@Entity
public class Department extends Time {
    @Id
    @GeneratedValue
    private Long id;

    private String departmentName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private List<Member> memberList;

    @OneToOne
    @JoinColumn(name = "leader_id")
    private Member leader;
}
