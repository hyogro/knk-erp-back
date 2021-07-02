package knk.erp.api.shlee.Fixtures.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_FixturesDTO {
    private Long fixturesId;
    private String fixturesName;
    private int amount;
    private boolean confirm;
    private String memo;
    private boolean purchase;
}
