package knk.erp.api.shlee.Fixtures.service;

import knk.erp.api.shlee.Fixtures.dto.*;
import knk.erp.api.shlee.Fixtures.entity.Fixtures;
import knk.erp.api.shlee.Fixtures.entity.FixturesForm;
import knk.erp.api.shlee.Fixtures.repository.FixturesFormRepository;
import knk.erp.api.shlee.Fixtures.repository.FixturesRepository;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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

    // 비품 요청 생성
    @Transactional
    public Create_FixturesFormDTO_RES createFixturesForm(FixturesFormDTO_REQ fixturesFormDTO_req){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member author = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            FixturesForm fixturesForm = fixturesFormDTO_req.toFixturesForm();
            fixturesForm.setAuthor(author);
            fixturesFormRepository.save(fixturesForm);
            return new Create_FixturesFormDTO_RES("CFF001");
        }catch(Exception e){
            return new Create_FixturesFormDTO_RES("CFF002", e.getMessage());
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
            return new Read_FixturesFormDTO_RES("RFF001", myFixtures);
        }catch(Exception e){
            return new Read_FixturesFormDTO_RES("RFF002",e.getMessage());
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
            return new ReadDetail_FixturesFormDTO_RES("RDFF001", new ReadDetail_FixturesFormDTO(fixtures, target.getAuthor().getMemberName(),
                    target.getAuthor().getMemberId(), target.getCreateDate().toLocalDate()));

        }catch(Exception e){
            return new ReadDetail_FixturesFormDTO_RES("RDFF002", e.getMessage());
        }
    }

    // 비품 요청서 수정
    @Transactional
    public Update_FixturesFormDTO_RES updateFixturesForm(Long fixturesFormId, Update_FixturesFormDTO_REQ updateFixturesFormDTOReq){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member my = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            FixturesForm targetForm = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

            if(!targetForm.getAuthor().equals(my)) return new Update_FixturesFormDTO_RES("UFF003", "작성자가 아님");

            if(targetForm.isCheck()) return new Update_FixturesFormDTO_RES("UFF004", "이미 처리된 요청서");

            for(Fixtures f : targetForm.getFixturesList()){
                fixturesRepository.delete(f);
            }

            List<Fixtures> fixtures = new ArrayList<>();
            for(Update_FixturesDTO_REQ u : updateFixturesFormDTOReq.getUpdateFixturesDTOReq()){
                fixtures.add(Fixtures.builder().fixturesName(u.getFixturesName())
                        .amount(u.getAmount())
                        .confirm(false)
                        .purchase(false)
                        .memo(u.getMemo())
                        .build());
            }

            targetForm.setFixturesList(fixtures);

            return new Update_FixturesFormDTO_RES("UFF001");
        }catch(Exception e){
            return new Update_FixturesFormDTO_RES("UFF002", e.getMessage());
        }
    }

    // 비품 요청서 삭제
    @Transactional
    public Delete_FixturesFormDTO_RES deleteFixturesForm(Long fixturesFormId){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member my = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            FixturesForm targetForm = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

            if(!targetForm.getAuthor().equals(my)) return new Delete_FixturesFormDTO_RES("DFF003", "작성자가 아님");

            if(targetForm.isCheck()) return new Delete_FixturesFormDTO_RES("DFF004", "이미 처리된 요청서");

            targetForm.setDeleted(true);
            fixturesFormRepository.save(targetForm);

            return new Delete_FixturesFormDTO_RES("DFF001");
        }catch(Exception e){
            return new Delete_FixturesFormDTO_RES("DFF002", e.getMessage());
        }
    }

    // 비품 요청서 목록 보기
    @Transactional
    public ReadAll_FixturesFormDTO_RES readAllFixturesForm(Pageable pageable, String searchType){
        try{
            pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, 15,
                    Sort.by("createDate").descending());
            List<FixturesForm> fixturesList;
            List<FixturesForm> pageSize;
            switch(searchType){
                case "미처리":
                    fixturesList = fixturesFormRepository.findAllByCheckIsFalseAndDeletedIsFalse(pageable);
                    pageSize = fixturesFormRepository.findAllByCheckIsFalseAndDeletedIsFalse();
                    break;

                case "처리완료":
                    fixturesList = fixturesFormRepository.findAllByCheckIsTrueAndDeletedIsFalse(pageable);
                    pageSize = fixturesFormRepository.findAllByCheckIsTrueAndDeletedIsFalse();
                    break;

                default:
                    fixturesList = fixturesFormRepository.findAllByDeletedIsFalse(pageable);
                    pageSize = fixturesFormRepository.findAllByDeletedIsFalse();
                    break;
            }
            Page<FixturesForm> fixturesPage = new PageImpl<>(fixturesList, pageable, fixturesList.size());
            Page<ReadAll_FixturesFormDTO> page = fixturesPage.map(fixturesForm -> new ReadAll_FixturesFormDTO(fixturesForm.getId(),
                    fixturesForm.getCreateDate().toLocalDate(), fixturesForm.isCheck()));

            int size = pageSize.size() / 15;
            if(pageSize.size()%15 != 0) size++;

            return new ReadAll_FixturesFormDTO_RES("RAFF001", page, size);

        }catch(Exception e){
            return new ReadAll_FixturesFormDTO_RES("RAFF002", e.getMessage());
        }
    }
}
