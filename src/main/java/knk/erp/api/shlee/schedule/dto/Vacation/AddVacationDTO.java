package knk.erp.api.shlee.schedule.dto.Vacation;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.AddVacation;
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
public class AddVacationDTO {
    private boolean increase;
    private int date;
    private String memo;
    private String receiverId;

    public AddVacation toEntity(){
        return AddVacation.builder().increase(increase).date(date).memo(memo).build();
    }
}
