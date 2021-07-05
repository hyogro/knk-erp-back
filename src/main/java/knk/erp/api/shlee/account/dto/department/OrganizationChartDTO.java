package knk.erp.api.shlee.account.dto.department;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationChartDTO {
    private String departmentName;
    private List<OrganizationChartMemberInfoDTO> organizationChartMemberInfoDTO;
}
