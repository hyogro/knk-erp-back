package knk.erp.api.shlee.domain.schedule.dto.Vacation;

import knk.erp.api.shlee.domain.schedule.entity.AddVacation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AddVacationDTO {
    private boolean increase;
    private int date;
    private String memo;
    private String receiverId;

    public AddVacation toEntity(){
        return AddVacation.builder().increase(increase).date(date).memo(memo).build();
    }
}
