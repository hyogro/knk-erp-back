package knk.erp.api.shlee.domain.Evaluation.entity;

import knk.erp.api.shlee.domain.schedule.entity.Time;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "Evaluation")
@Entity
public class Evaluation extends Time {
    @Id
    @GeneratedValue
    private Long idx;

    @Column
    private String evaluation;

    @Builder
    public Evaluation(String evaluation) { this.evaluation = evaluation; }
}
