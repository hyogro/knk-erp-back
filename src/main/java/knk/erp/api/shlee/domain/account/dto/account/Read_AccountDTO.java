package knk.erp.api.shlee.domain.account.dto.account;

import lombok.*;

import java.time.LocalDate;

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
    private String position;
}
