package knk.erp.api.shlee.Fixtures.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Confirm_FixturesDTO {
    private List<Long> fixturesId;
    private boolean confirm;
}
