package knk.erp.api.shlee.Fixtures.controller;

import knk.erp.api.shlee.Fixtures.service.FixturesFormService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fixtures")
public class FixturesFormController {
    FixturesFormService fixturesFormService;

    public FixturesFormController(FixturesFormService fixturesFormService) {
        this.fixturesFormService = fixturesFormService;
    }

    //POST 생성

    //GET 내가쓴거 목록 가져오기

    //GET 내가쓴거 상세 가져오기

    //PUT 내가쓴거 수정하기

    //DELETE 내가쓴거 삭제하기 isDeleted = true;

    //GET 남이쓴거 목록 가져오기(페이징, 권한)

    //PUT 품목에 대한 승인, 거절 여부 변경

    //PUT 품목에 대한 구매 여부 변경

}
