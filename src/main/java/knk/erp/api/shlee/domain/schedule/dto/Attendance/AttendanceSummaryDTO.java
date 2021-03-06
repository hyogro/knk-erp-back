package knk.erp.api.shlee.domain.schedule.dto.Attendance;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class AttendanceSummaryDTO{
    private List<MemberDepartmentNameDTO> onWork;
    private List<MemberDepartmentNameDTO> offWork;
    private List<MemberDepartmentNameDTO> yetWork;
    private List<MemberDepartmentNameDTO> lateWork;
    private List<MemberDepartmentNameDTO> vacation;
}
