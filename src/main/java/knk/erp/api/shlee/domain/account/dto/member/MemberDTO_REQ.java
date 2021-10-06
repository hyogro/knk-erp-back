package knk.erp.api.shlee.domain.account.dto.member;

import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.entity.Member;
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
    private Long departmentId;
    private LocalDate joiningDate;
    private String address;
    private String email;
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
