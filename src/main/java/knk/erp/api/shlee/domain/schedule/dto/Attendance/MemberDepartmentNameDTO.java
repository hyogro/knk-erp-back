package knk.erp.api.shlee.domain.schedule.dto.Attendance;

import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.schedule.entity.Attendance;
import knk.erp.api.shlee.domain.schedule.entity.Vacation;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberDepartmentNameDTO {
    private String memberId;
    private String memberName;
    private String departmentName;
    private LocalTime onWork;
    private LocalTime offWork;
    private LocalDateTime vacationStartDate;
    private LocalDateTime vacationEndDate;
    private String phone;

    public MemberDepartmentNameDTO(Member member){
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.departmentName = member.getDepartment().getDepartmentName();
        this.phone = member.getPhone();
    }
    public MemberDepartmentNameDTO(Vacation vacation){
        this.memberId = vacation.getAuthor().getMemberId();
        this.memberName = vacation.getAuthor().getMemberName();
        this.departmentName = vacation.getAuthor().getDepartment().getDepartmentName();
        this.vacationStartDate = vacation.getStartDate();
        this.vacationEndDate = vacation.getEndDate();
    }
    public MemberDepartmentNameDTO(Attendance attendance){
        this.memberId = attendance.getAuthor().getMemberId();
        this.memberName = attendance.getAuthor().getMemberName();
        this.departmentName = attendance.getAuthor().getDepartment().getDepartmentName();
        this.onWork = attendance.getOnWork();
        this.offWork = attendance.getOffWork();
    }

}
