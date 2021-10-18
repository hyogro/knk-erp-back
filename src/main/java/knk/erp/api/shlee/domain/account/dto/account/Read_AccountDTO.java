package knk.erp.api.shlee.domain.account.dto.account;

import knk.erp.api.shlee.domain.account.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_AccountDTO {
    private String memberId;
    private String memberName;
    private String departmentName;
    private String phone;
    private LocalDate joiningDate;
    private String position;

    public Read_AccountDTO(Member member){
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.departmentName = member.getDepartment().getDepartmentName();
        this.phone = member.getPhone();
        this.joiningDate = member.getJoiningDate();
        this.position = member.getPosition();
    }
}
