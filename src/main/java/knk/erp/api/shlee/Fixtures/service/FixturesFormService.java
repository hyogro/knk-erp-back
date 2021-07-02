package knk.erp.api.shlee.Fixtures.service;

import knk.erp.api.shlee.Fixtures.dto.Create_FixturesFormDTO_RES;
import knk.erp.api.shlee.Fixtures.dto.FixturesFormDTO_REQ;
import knk.erp.api.shlee.Fixtures.dto.Read_FixturesFormDTO;
import knk.erp.api.shlee.Fixtures.dto.Read_FixturesFormDTO_RES;
import knk.erp.api.shlee.Fixtures.entity.FixturesForm;
import knk.erp.api.shlee.Fixtures.repository.FixturesFormRepository;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FixturesFormService {
    private final FixturesFormRepository fixturesFormRepository;
    private final MemberRepository memberRepository;

    // 비품 요청 생성
    @Transactional
    public Create_FixturesFormDTO_RES createFixturesForm(FixturesFormDTO_REQ fixturesFormDTO_req){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member author = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            FixturesForm fixturesForm = fixturesFormDTO_req.toFixturesForm();
            fixturesForm.setAuthor(author);
            fixturesFormRepository.save(fixturesForm);
            return new Create_FixturesFormDTO_RES("CFT001");
        }catch(Exception e){
            return new Create_FixturesFormDTO_RES("CFT002", e.getMessage());
        }
    }

    // 내 비품 요청 목록 읽기
    @Transactional
    public Read_FixturesFormDTO_RES readFixturesFormList(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member author = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            List<FixturesForm> data = fixturesFormRepository.findAllByAuthorAndDeletedIsFalse(author);
            List<Read_FixturesFormDTO> myFixtures = new ArrayList<>();
            for(FixturesForm form : data){
                myFixtures.add(new Read_FixturesFormDTO(form.getId(), form.getCreateDate().toLocalDate(), form.isCheck()));
            }
            return new Read_FixturesFormDTO_RES("RFT001", myFixtures);
        }catch(Exception e){
            return new Read_FixturesFormDTO_RES("RFT002",e.getMessage());
        }
    }
}
