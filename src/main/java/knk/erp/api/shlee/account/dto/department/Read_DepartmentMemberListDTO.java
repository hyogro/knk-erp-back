package knk.erp.api.shlee.account.dto.department;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_DepartmentMemberListDTO {
    private String memberId;
    private String memberName;
}