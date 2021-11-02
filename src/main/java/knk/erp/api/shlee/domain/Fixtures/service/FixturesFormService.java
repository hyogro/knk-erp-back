package knk.erp.api.shlee.domain.Fixtures.service;

import knk.erp.api.shlee.domain.Fixtures.dto.*;
import knk.erp.api.shlee.domain.Fixtures.entity.Fixtures;
import knk.erp.api.shlee.domain.Fixtures.entity.FixturesForm;
import knk.erp.api.shlee.domain.Fixtures.repository.FixturesFormRepository;
import knk.erp.api.shlee.domain.Fixtures.repository.FixturesRepository;
import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.exception.exceptions.Fixtures.FixturesFormCheckedException;
import knk.erp.api.shlee.exception.exceptions.Fixtures.FixturesFormNotAuthorException;
import knk.erp.api.shlee.exception.exceptions.Fixtures.FixturesFormNotFoundException;
import knk.erp.api.shlee.exception.exceptions.Fixtures.FixturesNotFoundException;
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

    /* 비품 요청 생성 */
    @Transactional
    public void createFixturesForm(FixturesFormDTO_REQ fixturesFormDTO_req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member author = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
        FixturesForm fixturesForm = fixturesFormDTO_req.toFixturesForm();
        fixturesForm.setAuthor(author);
        fixturesFormRepository.save(fixturesForm);
    }

    /* 내 비품 요청 목록 읽기 */
    @Transactional
    public List<Read_FixturesFormDTO> readFixturesFormList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member author = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
        List<FixturesForm> data = fixturesFormRepository.findAllByAuthorAndDeletedIsFalse(author);
        List<Read_FixturesFormDTO> myFixtures = new ArrayList<>();
        for(FixturesForm form : data){
            myFixtures.add(new Read_FixturesFormDTO(form.getId(), form.getCreateDate().toLocalDate(), form.isCheck()));
        }

        return myFixtures;
    }

    /* 비품 요청서 상세보기 */
    @Transactional
    public ReadDetail_FixturesFormDTO readDetailFixturesForm(Long fixturesFormId){
        throwIfNotFoundFixturesForm(fixturesFormId);
        FixturesForm target = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

        List<ReadDetail_FixturesDTO> fixtures = new ArrayList<>();
        for(Fixtures f : target.getFixturesList()){
            fixtures.add(new ReadDetail_FixturesDTO(f.getId(), f.getFixturesName(), f.getAmount(), f.isConfirm(), f.getMemo(),
                    f.isPurchase()));
        }

        return new ReadDetail_FixturesFormDTO(fixtures, target.getAuthor().getMemberName(), target.getAuthor().getMemberId(),
                target.getCreateDate().toLocalDate(), target.isCheck());
    }

    //삭제되었거나 존재하지않는 비품요청서 예외처리
    public void throwIfNotFoundFixturesForm(Long fixturesFormId){
        if(!fixturesFormRepository.existsByIdAndDeletedFalse(fixturesFormId)){
            throw new FixturesFormNotFoundException();
        }
    }

    /* 내 비품 요청서 수정 */
    @Transactional
    public void updateFixturesForm(Long fixturesFormId, Update_FixturesFormDTO_REQ updateFixturesFormDTOReq){
        throwIfNotFoundFixturesForm(fixturesFormId);
        FixturesForm targetForm = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

        throwIfNotAuthor(targetForm);
        throwIfChecked(targetForm);

        fixturesRepository.deleteAll(targetForm.getFixturesList());

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
    }

    // 작성자가 아닌 사람이 비품 요청서 수정, 삭제 시도 예외 처리
    public void throwIfNotAuthor(FixturesForm targetForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member my = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());

        if(!targetForm.getAuthor().equals(my)) {
            throw new FixturesFormNotAuthorException();
        }
    }

    // 이미 처리된 비품 요청서 수정, 삭제 시도 예외 처리
    public void throwIfChecked(FixturesForm targetForm){
        if(targetForm.isCheck()) {
            throw new FixturesFormCheckedException();
        }
    }

    /* 내 비품 요청서 삭제 */
    @Transactional
    public void deleteFixturesForm(Long fixturesFormId){
        throwIfNotFoundFixturesForm(fixturesFormId);
        FixturesForm targetForm = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

        throwIfNotAuthor(targetForm);
        throwIfChecked(targetForm);

        targetForm.setDeleted(true);
        fixturesFormRepository.save(targetForm);
    }

    /* 비품 요청서 목록 보기 */
    @Transactional
    public ReadAll_FixturesFormDTO_RES readAllFixturesForm(Pageable pageable, String searchType){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, 15,
                Sort.by("createDate").descending());
        List<FixturesForm> fixturesList;
        int pageSize;
        switch(searchType){
            case "승인미처리":
                fixturesList = fixturesFormRepository.findAllByCheckIsFalseAndDeletedIsFalse(pageable);
                pageSize = fixturesFormRepository.countAllByCheckIsFalseAndDeletedIsFalse();
                break;

            case "승인처리완료":
                fixturesList = fixturesFormRepository.findAllByCheckIsTrueAndDeletedIsFalse(pageable);
                pageSize = fixturesFormRepository.countAllByCheckIsTrueAndDeletedIsFalse();
                break;

            default:
                fixturesList = fixturesFormRepository.findAllByDeletedIsFalse(pageable);
                pageSize = fixturesFormRepository.countAllByDeletedIsFalse();
                break;
        }
        Page<FixturesForm> fixturesPage = new PageImpl<>(fixturesList, pageable, fixturesList.size());
        Page<ReadAll_FixturesFormDTO> page = fixturesPage.map(fixturesForm -> new ReadAll_FixturesFormDTO(fixturesForm.getId(),
                fixturesForm.getCreateDate().toLocalDate(), fixturesForm.isCheck(), fixturesForm.getAuthor().getMemberName()));

        int size = pageSize / 15;
        if(pageSize % 15 != 0) size++;

        return new ReadAll_FixturesFormDTO_RES(page, size);
    }

    /* 비품 승인 및 거절 */
    @Transactional
    public void confirmFixtures(Long fixturesFormId, Confirm_FixturesDTO confirmFixturesDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member approver = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());

        throwIfNotFoundFixturesForm(fixturesFormId);
        FixturesForm fixturesForm = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

        for(Long i : confirmFixturesDTO.getFixturesId()){
            throwIfNotFoundFixtures(i);

            Fixtures target = fixturesRepository.findByIdAndDeletedIsFalse(i);
            target.setConfirm(confirmFixturesDTO.isConfirm());
            fixturesRepository.save(target);
        }

        fixturesForm.setCheck(true);
        fixturesForm.setApprover(approver);
        fixturesFormRepository.save(fixturesForm);
    }

    // 삭제되었거나 존재하지않는 비품요청항목 예외처리
    public void throwIfNotFoundFixtures(Long fixturesId){
        if(!fixturesRepository.existsByIdAndDeletedIsFalse(fixturesId)) {
            throw new FixturesNotFoundException();
        }
    }

    /* 비품 구매 여부 변경 */
    public void purchaseFixtures(Long fixturesFormId, Purchase_FixturesDTO purchaseFixturesDTO){
        throwIfNotFoundFixturesForm(fixturesFormId);
        FixturesForm fixturesForm = fixturesFormRepository.findByIdAndDeletedIsFalse(fixturesFormId);

        for(Long i : purchaseFixturesDTO.getFixturesId()){
            throwIfNotFoundFixtures(i);

            Fixtures target = fixturesRepository.findByIdAndDeletedIsFalse(i);
            target.setPurchase(purchaseFixturesDTO.isPurchase());
            fixturesRepository.save(target);
        }
        fixturesFormRepository.save(fixturesForm);
    }
}
