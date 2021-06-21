package knk.erp.api.shlee.schedule.dto.Vacation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VacationInfo {
    private int totalVacation;
    private int usedVacation;
    private int addVacation;

    public VacationInfo(int totalVacation, int usedVacation, int addVacation) {
        this.totalVacation = totalVacation;
        this.usedVacation = usedVacation;
        this.addVacation = addVacation;
    }
}
