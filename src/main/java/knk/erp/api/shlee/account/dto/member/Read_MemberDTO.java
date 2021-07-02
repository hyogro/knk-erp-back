package knk.erp.api.shlee.account.dto.member;

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
}
