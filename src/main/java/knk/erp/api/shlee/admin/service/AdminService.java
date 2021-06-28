package knk.erp.api.shlee.admin.service;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public String makeAdmin(){
        String memberId = "admin";
        String password = passwordEncoder.encode("admin1234");
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

    public String makeDefaultUser(){
        String division = "#";
        String divisionLine = "!";
        String data ="";
        

        String[] memberArray = data.split(divisionLine);

        int index = 1;
        for(String member : memberArray){
            String[] memberData = member.split(division);
            String memberId = "knk" + ((index < 10) ? "00": "0") + index;
            String password = passwordEncoder.encode("00000000");
            String memberName = memberData[0];
            String[] dateData = memberData[1].split("\\.");
            System.out.println(memberData.toString());
            System.out.println(dateData.toString());

            LocalDate joiningDate = LocalDate.of(Integer.parseInt(dateData[0]), Integer.parseInt(dateData[1]), Integer.parseInt(dateData[2]));

            String phone = memberData[2];
            String email = memberData[3];
            String address = memberData[4];

            try {
                Member memberEntity = Member.builder().memberId(memberId).password(password).memberName(memberName).phone(phone).email(email).address(address).joiningDate(joiningDate).build();
                //System.out.println(memberEntity.toString());
                memberRepository.save(memberEntity);
            }catch (Exception e){
                return "failed: " + e.getMessage();
            }
            index++;
        }

        return "success";
    }
}
