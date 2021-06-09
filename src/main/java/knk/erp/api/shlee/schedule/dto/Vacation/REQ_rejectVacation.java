package knk.erp.api.shlee.schedule.dto.Vacation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class REQ_rejectVacation {
    private Long id;
    private String rejectMemo;
}
