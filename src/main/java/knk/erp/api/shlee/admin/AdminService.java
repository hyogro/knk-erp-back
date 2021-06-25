package knk.erp.api.shlee.admin;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    public String makeAdmin(){
        String memberId = "admin";
        String password = "admin1234";
        String memberName = "관리자";
        //Long departmentId = 1L;
        String phone = "000-0000-0000";
        LocalDate joiningDate = LocalDate.now();

        try {

            Member member = Member.builder().memberId(memberId).password(password).memberName(memberName).phone(phone).joiningDate(joiningDate).build();
            memberRepository.save(member);
            return "success";
        }catch (Exception e){
            return "failed: " + e.getMessage();
        }
    }
}
