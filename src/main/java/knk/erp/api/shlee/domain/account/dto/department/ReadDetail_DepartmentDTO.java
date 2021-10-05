package knk.erp.api.shlee.domain.account.dto.department;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_DepartmentDTO {
    private String departmentName;
    private String leaderId;
    private String leaderName;
    private int headCount;
}
