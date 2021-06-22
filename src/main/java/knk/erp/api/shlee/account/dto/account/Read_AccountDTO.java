package knk.erp.api.shlee.account.dto.account;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_AccountDTO {
    private String memberId;
    private String memberName;
    private String departmentName;
    private String phone;
    private LocalDate joiningDate;
}
