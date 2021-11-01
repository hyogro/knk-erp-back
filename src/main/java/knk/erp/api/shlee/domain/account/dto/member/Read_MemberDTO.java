package knk.erp.api.shlee.domain.account.dto.member;

import knk.erp.api.shlee.domain.account.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_MemberDTO {
    private String memberId;
    private String password;
    private String phone;
    private String memberName;
    private int vacation;
    private String departmentName;
    private String authority;
    private String address;
    private String email;
    private LocalDate joiningDate;
    private String images;
    private LocalDate birthDate;
    private boolean birthDateSolar;
    private String position;

    public Read_MemberDTO(Member member){
        this.memberId = member.getMemberId();
        this.password = null;
        this.phone = member.getPhone();
        this.memberName = member.getMemberName();
        this.vacation = member.getVacation();
        this.departmentName = member.getDepartment().getDepartmentName();
        this.authority = member.getAuthority().toString();
        this.address = member.getAddress();
        this.email = member.getEmail();
        this.joiningDate = member.getJoiningDate();
        this.images = member.getImages();
        this.birthDate = member.getBirthDate();
        this.birthDateSolar = member.isBirthDateSolar();
        this.position = member.getPosition();
    }
}
