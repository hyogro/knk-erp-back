package knk.erp.api.shlee.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SignUp_DepartmentDTO {
    private Long dep_id;
    private String dep_name;
}
