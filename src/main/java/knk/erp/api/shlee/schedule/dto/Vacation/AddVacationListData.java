package knk.erp.api.shlee.schedule.dto.Vacation;

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
public class AddVacationListData {
    private Long id;
    private boolean increase;
    private int date;
    private String giverId;
    private String giverName;
    private String receiverId;
    private String receiverName;

    public AddVacationListData(AddVacation addVacation){
        this.id = addVacation.getId();
        this.increase = addVacation.isIncrease();
        this.date = addVacation.getDate();
        this.giverId = addVacation.getGiver().getMemberId();
        this.giverName = addVacation.getGiver().getMemberName();
        this.receiverId = addVacation.getReceiver().getMemberId();
        this.receiverName = addVacation.getReceiver().getMemberName();
    }
}
