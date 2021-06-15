package knk.erp.api.shlee.schedule.dto.Vacation;

import knk.erp.api.shlee.schedule.entity.Vacation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class VacationDTO {
    private Long id;
    private String type;
    private String memo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime requestDate;

    public Vacation toEntity(){
        return Vacation.builder().type(type).memo(memo).startDate(startDate).endDate(endDate).build();
    }
}
