package knk.erp.api.shlee.domain.account.dto.member;

import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO_REQ {
    private Long id;

    @NotEmpty
    @Size(min = 6, max = 16)
    private String memberId;

    @NotEmpty
    @Size(min = 8, max = 16)
    private String password;

    @NotEmpty
    private String phone;

    @NotEmpty
    @Size(min = 2, max = 5)
    private String memberName;

    private Long departmentId;

    @NotEmpty
    private LocalDate joiningDate;

    private String address;
    private String email;

    @NotEmpty
    private String position;

    private Authority authority;

    public Member toMember(BCryptPasswordEncoder passwordEncoder){
        //이상훈 테스트 위해 추가
        if(this.authority == null) this.authority = Authority.ROLE_LVL1;

        return Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .memberName(memberName)
                .vacation(0)
                .authority(authority)
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
