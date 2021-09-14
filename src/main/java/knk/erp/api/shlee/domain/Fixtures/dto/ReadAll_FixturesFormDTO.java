package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadAll_FixturesFormDTO {
    private Long id;
    private LocalDate createDate;
    private boolean check;
    private String authorName;
}
