package knk.erp.api.shlee.domain.account.dto.department;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_DepartmentDTO_RES {
    private ReadDetail_DepartmentDTO readDetailDepartmentDTO;
    private List<Read_DepartmentMemberListDTO> readDepartmentMemberListDTO;


}
