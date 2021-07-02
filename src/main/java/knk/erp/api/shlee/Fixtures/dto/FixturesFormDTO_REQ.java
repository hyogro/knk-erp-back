package knk.erp.api.shlee.Fixtures.dto;

import knk.erp.api.shlee.Fixtures.entity.Fixtures;
import knk.erp.api.shlee.Fixtures.entity.FixturesForm;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FixturesFormDTO_REQ {
    private List<FixturesDTO_REQ> fixturesDTOReq;

    public FixturesForm toFixturesForm(){
        List<Fixtures> fixturesList = new ArrayList<>();
        for(FixturesDTO_REQ f : fixturesDTOReq){
            fixturesList.add(f.toFixtures());
        }
        return FixturesForm.builder()
                .fixturesList(fixturesList)
                .check(false)
                .build();
    }
}
