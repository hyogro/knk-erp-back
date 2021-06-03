package knk.erp.api.shlee.account.entity;

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
public class Department {
    @Id
    @GeneratedValue
    private Long id;

    private String departmentName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private List<Member> memberList;
}