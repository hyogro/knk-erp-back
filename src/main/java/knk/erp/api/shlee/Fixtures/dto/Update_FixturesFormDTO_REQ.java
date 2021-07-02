package knk.erp.api.shlee.Fixtures.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_FixturesFormDTO_REQ {
    List<Update_FixturesDTO_REQ> updateFixturesDTOReq;
}
