package knk.erp.api.shlee.domain.account.dto.member;

import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_AccountDTO_REQ {
    @Size(min = 8, max = 16)
    private String password;
    private String authority;
    private String phone;
    private String vacation;
    private Long dep_id;
    private LocalDate joiningDate;
    private String address;
    private String email;
    private String images;
    private LocalDate birthDate;
    private boolean birthDateSolar;
    private String position;
}
