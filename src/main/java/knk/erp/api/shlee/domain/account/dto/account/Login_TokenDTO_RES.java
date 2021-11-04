package knk.erp.api.shlee.domain.account.dto.account;

import knk.erp.api.shlee.common.dto.TokenDto;
import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Login_TokenDTO_RES {
    private TokenDto tokenDto;
    private String memberName;

    public Login_TokenDTO_RES(TokenDto tokenDto, String memberName) {
        this.tokenDto = tokenDto;
        this.memberName = memberName;
    }
}
