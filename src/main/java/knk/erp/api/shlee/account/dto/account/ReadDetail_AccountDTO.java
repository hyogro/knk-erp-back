package knk.erp.api.shlee.account.dto.account;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_AccountDTO {
    private String memberId;
    private String memberName;
    private String password;
    private String authority;
    private String phone;
    private int vacation;
    private String departmentName;
    private String address;
    private String email;
    LocalDate joiningDate;
}
