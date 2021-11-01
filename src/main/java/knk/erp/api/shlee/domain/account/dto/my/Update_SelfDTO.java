package knk.erp.api.shlee.domain.account.dto.my;

import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Update_SelfDTO {
    @Size(min = 8, max = 16)
    private String password;
    private String phone;
    private String email;
    private String address;
    private LocalDate birthDate;
    private boolean birthDateSolar;
    private String images;
}
