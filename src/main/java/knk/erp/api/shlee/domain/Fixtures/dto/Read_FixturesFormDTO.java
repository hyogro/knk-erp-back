package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_FixturesFormDTO {
    private Long fixturesFormId;
    private LocalDate createDate;
    private boolean check;
}
