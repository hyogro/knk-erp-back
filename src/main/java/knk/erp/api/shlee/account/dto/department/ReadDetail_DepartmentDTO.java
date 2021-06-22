package knk.erp.api.shlee.account.dto.department;

import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_DepartmentDTO {
    private String departmentName;
    private String leaderName;
    private int headCount;
}
