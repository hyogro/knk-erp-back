package knk.erp.api.shlee.domain.account.dto.account;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Check_existMemberIdDTO {
    private String memberId;
    private boolean dummy;
}
