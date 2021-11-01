package knk.erp.api.shlee.domain.account.dto.department;

import knk.erp.api.shlee.domain.account.entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_DepartmentMemberListDTO {
    private String memberId;
    private String memberName;

    public Read_DepartmentMemberListDTO(Member member) {
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
    }
}
