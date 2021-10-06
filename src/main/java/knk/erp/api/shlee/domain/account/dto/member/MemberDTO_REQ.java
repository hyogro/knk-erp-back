package knk.erp.api.shlee.domain.account.dto.member;

import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO_REQ {
    private Long id;
    @NotEmpty
    private String memberId;
    @NotEmpty
    private String password;
    private String phone;
    private String memberName;
    private Long departmentId;
    private LocalDate joiningDate;
    private String address;
    private String email;
    private String position;

    public Member toMember(BCryptPasswordEncoder passwordEncoder){
        return Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .memberName(memberName)
                .vacation(0)
                .authority(Authority.ROLE_LVL1)
                .joiningDate(joiningDate)
                .address(address)
                .email(email)
                .position(position)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(memberId, password);
    }
}
