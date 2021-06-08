package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.my.*;
import knk.erp.api.shlee.account.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyPageController {
    private final MyPageService myPageService;

    @PostMapping("/{memberId}")
    public ResponseEntity<getMyInfo_MyPageDTO_RES> getmyinfo(@PathVariable String memberId){
        return ResponseEntity.ok(myPageService.getmyinfo(memberId));
    }


}
