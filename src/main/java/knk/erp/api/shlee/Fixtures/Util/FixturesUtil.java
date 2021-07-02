package knk.erp.api.shlee.Fixtures.Util;

import knk.erp.api.shlee.Fixtures.dto.Update_FixturesDTO_REQ;
import knk.erp.api.shlee.Fixtures.entity.Fixtures;
import org.springframework.stereotype.Component;

@Component
public class FixturesUtil {

    public void updateFixtures(Fixtures fixture, String fixturesName, int amount, String memo){
        fixture.setFixturesName(fixturesName);
        fixture.setAmount(amount);
        fixture.setMemo(memo);
    }
}
