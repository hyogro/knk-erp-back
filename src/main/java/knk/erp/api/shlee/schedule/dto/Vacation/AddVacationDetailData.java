package knk.erp.api.shlee.schedule.dto.Vacation;

import knk.erp.api.shlee.schedule.entity.AddVacation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AddVacationDetailData {
    private Long id;
    private boolean increase;
    private int date;
    private String memo;
    private String giverId;
    private String giverName;
    private String receiverId;
    private String receiverName;

    public AddVacationDetailData(AddVacation addVacation){
        this.id = addVacation.getId();
        this.increase = addVacation.isIncrease();
        this.date = addVacation.getDate();
        this.memo = addVacation.getMemo();
        this.giverId = addVacation.getGiver().getMemberId();
        this.giverName = addVacation.getGiver().getMemberName();
        this.receiverId = addVacation.getReceiver().getMemberId();
        this.receiverName = addVacation.getReceiver().getMemberName();
    }
}
