package knk.erp.api.shlee.Fixtures.service;

import knk.erp.api.shlee.Fixtures.dto.Create_FixturesDTO_RES;
import knk.erp.api.shlee.Fixtures.dto.FixturesFormDTO_REQ;
import knk.erp.api.shlee.Fixtures.entity.FixturesForm;
import knk.erp.api.shlee.Fixtures.repository.FixturesFormRepository;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FixturesFormService {
    private final FixturesFormRepository fixturesFormRepository;
    private final MemberRepository memberRepository;

    // 비품 요청 생성
    @Transactional
    public Create_FixturesDTO_RES createFixtures(FixturesFormDTO_REQ fixturesFormDTO_req){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member author = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            FixturesForm fixturesForm = fixturesFormDTO_req.toFixturesForm();
            fixturesForm.setAuthor(author);
            fixturesFormRepository.save(fixturesForm);
            return new Create_FixturesDTO_RES("CFT001");
        }catch(Exception e){
            return new Create_FixturesDTO_RES("CFT002", e.getMessage());
        }
    }
}
