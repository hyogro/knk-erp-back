package knk.erp.api.shlee.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// Token 재발급을 위한 AccessToken, RefreshToken
@Getter
@NoArgsConstructor
public class TokenRequestDto {
    private String accessToken;
}
