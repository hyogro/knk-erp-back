package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_FixturesDTO_REQ {
    private String fixturesName;
    private int amount;
    private String memo;
}
