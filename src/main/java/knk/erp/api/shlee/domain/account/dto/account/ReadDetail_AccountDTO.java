package knk.erp.api.shlee.domain.account.dto.account;

import knk.erp.api.shlee.domain.account.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadDetail_AccountDTO {
    private String memberId;
    private String memberName;
    private String password;
    private String authority;
    private String phone;
    private int vacation;
    private Long dep_id;
    private String departmentName;
    private String address;
    private String email;
    private LocalDate joiningDate;
    private String images;
    private LocalDate birthDate;
    private boolean birthDateSolar;
    private String position;

    public ReadDetail_AccountDTO(Member member){
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.password = null;
        this.authority = member.getAuthority().toString();
        this.phone = member.getPhone();
        this.vacation = member.getVacation();
        this.dep_id = member.getDepartment().getId();
        this.departmentName = member.getDepartment().getDepartmentName();
        this.address = member.getAddress();
        this.email = member.getEmail();
        this.joiningDate = member.getJoiningDate();
        this.images = member.getImages();
        this.birthDate = member.getBirthDate();
        this.position = member.getPosition();
    }
}
