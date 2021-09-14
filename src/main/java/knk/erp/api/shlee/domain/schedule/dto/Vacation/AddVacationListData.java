package knk.erp.api.shlee.domain.schedule.dto.Vacation;

import knk.erp.api.shlee.domain.schedule.entity.AddVacation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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
    private LocalDate createDate;

    public AddVacationListData(AddVacation addVacation){
        this.id = addVacation.getId();
        this.increase = addVacation.isIncrease();
        this.date = addVacation.getDate();
        this.giverId = addVacation.getGiver().getMemberId();
        this.giverName = addVacation.getGiver().getMemberName();
        this.receiverId = addVacation.getReceiver().getMemberId();
        this.receiverName = addVacation.getReceiver().getMemberName();
        this.createDate = addVacation.getCreateDate().toLocalDate();
    }
}
