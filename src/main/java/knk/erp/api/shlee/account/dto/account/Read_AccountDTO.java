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
    private List<String> memberId;
    private List<String> memberName;
    private List<String> departmentName;
    private List<String> phone;
    private List<LocalDate> joiningDate;
}
