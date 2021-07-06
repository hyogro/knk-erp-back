package knk.erp.api.shlee.Fixtures.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_FixturesFormDTO {
    private List<ReadDetail_FixturesDTO> readDetailFixturesDTO;
    private String authorName;
    private String authorId;
    private LocalDate createDate;
    private boolean check;
}
