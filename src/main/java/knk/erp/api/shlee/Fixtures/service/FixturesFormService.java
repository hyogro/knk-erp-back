package knk.erp.api.shlee.Fixtures.service;

import knk.erp.api.shlee.Fixtures.Util.FixturesUtil;
import knk.erp.api.shlee.Fixtures.dto.*;
import knk.erp.api.shlee.Fixtures.entity.Fixtures;
import knk.erp.api.shlee.Fixtures.entity.FixturesForm;
import knk.erp.api.shlee.Fixtures.repository.FixturesFormRepository;
import knk.erp.api.shlee.Fixtures.repository.FixturesRepository;
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
    private final FixturesRepository fixturesRepository;
    private final FixturesUtil fixturesUtil;

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

    // 비품 요청서 상세보기
    @Transactional
    public ReadDetail_FixturesFormDTO_RES readDetailFixturesForm(Long fixturesFormId){
        try{
            FixturesForm target = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);
            List<ReadDetail_FixturesDTO> fixtures = new ArrayList<>();
            for(Fixtures f : target.getFixturesList()){
                fixtures.add(new ReadDetail_FixturesDTO(f.getId(), f.getFixturesName(), f.getAmount(), f.isConfirm(), f.getMemo(), f.isPurchase()));
            }
            return new ReadDetail_FixturesFormDTO_RES("RDFT001", new ReadDetail_FixturesFormDTO(fixtures, target.getAuthor().getMemberName(),
                    target.getAuthor().getMemberId(), target.getCreateDate().toLocalDate()));

        }catch(Exception e){
            return new ReadDetail_FixturesFormDTO_RES("RDFT002", e.getMessage());
        }
    }

    // 비품 요청서 수정
    @Transactional
    public Update_FixturesFormDTO_RES updateFixturesForm(Long fixturesFormId, Update_FixturesFormDTO_REQ updateFixturesFormDTOReq){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member my = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            FixturesForm targetForm = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

            if(!targetForm.getAuthor().equals(my)) return new Update_FixturesFormDTO_RES("UFT003", "작성자가 아님");

            if(targetForm.isCheck()) return new Update_FixturesFormDTO_RES("UFT004", "이미 처리된 요청서");

            for(Update_FixturesDTO_REQ u : updateFixturesFormDTOReq.getUpdateFixturesDTOReq()){
                Fixtures fixture = fixturesRepository.findByIdAndDeletedIsFalse(u.getFixturesId());
                fixturesUtil.updateFixtures(fixture, u.getFixturesName(), u.getAmount(), u.getMemo());
                fixturesRepository.save(fixture);
            }

            return new Update_FixturesFormDTO_RES("UFT001");
        }catch(Exception e){
            return new Update_FixturesFormDTO_RES("UFT002", e.getMessage());
        }
    }

    // 비품 요청서 삭제
    public Delete_FixturesFormDTO_RES deleteFixturesForm(Long fixturesFormId){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member my = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            FixturesForm targetForm = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

            if(!targetForm.getAuthor().equals(my)) return new Delete_FixturesFormDTO_RES("DFT003", "작성자가 아님");

            if(targetForm.isCheck()) return new Delete_FixturesFormDTO_RES("DFT004", "이미 처리된 요청서");

            targetForm.setDeleted(true);
            fixturesFormRepository.save(targetForm);

            return new Delete_FixturesFormDTO_RES("DFT001");
        }catch(Exception e){
            return new Delete_FixturesFormDTO_RES("DFT002", e.getMessage());
        }
    }
}
