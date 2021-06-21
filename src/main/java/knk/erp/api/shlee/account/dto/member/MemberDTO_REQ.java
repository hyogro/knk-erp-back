package knk.erp.api.shlee.account.dto.member;

import knk.erp.api.shlee.account.entity.Authority;
import knk.erp.api.shlee.account.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO_REQ {
    private Long id;
    private String memberId;
    private String password;
    private String phone;
    private String memberName;
    private int vacation;
    private Long departmentId;
    private LocalDate joiningDate;

    public Member toMember(BCryptPasswordEncoder passwordEncoder){
        return Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .memberName(memberName)
                .vacation(vacation)
                .authority(Authority.ROLE_LVL1)
                .joiningDate(joiningDate)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(memberId, password);
    }
}
