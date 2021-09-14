package knk.erp.api.shlee.domain.Fixtures.dto;

import knk.erp.api.shlee.domain.Fixtures.entity.Fixtures;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FixturesDTO_REQ {
    private String fixturesName;
    private int amount;
    private String memo;

    public Fixtures toFixtures(){
        return Fixtures.builder()
                .fixturesName(fixturesName)
                .amount(amount)
                .confirm(false)
                .purchase(false)
                .memo(memo)
                .build();
    }
}
