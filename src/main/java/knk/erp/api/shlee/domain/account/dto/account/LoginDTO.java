package knk.erp.api.shlee.domain.account.dto.account;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotEmpty
    @Size(min = 6, max = 16)
    private String memberId;

    @NotEmpty
    @Size(min = 8, max = 16)
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(memberId, password);
    }
}
