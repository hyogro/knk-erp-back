package knk.erp.api.shlee.Materials.entity;

import knk.erp.api.shlee.board.util.StringListConverter;
import knk.erp.api.shlee.schedule.entity.Time;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "Materials")
@Entity
public class Materials extends Time {
    @Id
    @GeneratedValue
    private Long idx;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> materials;

    @Builder
    public Materials(List<String> materials) {
        this.materials = materials;
    }
}
