package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Purchase_FixturesDTO {
    private List<Long> fixturesId;
    private boolean purchase;
}
