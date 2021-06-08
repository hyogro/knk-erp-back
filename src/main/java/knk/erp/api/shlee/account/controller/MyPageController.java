package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.my.*;
import knk.erp.api.shlee.account.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyPageController {
    private final MyPageService myPageService;

    @PostMapping("/{memberId}")
    public ResponseEntity<GetMyInfo_MyPageDTO_RES> getmyinfo(@PathVariable String memberId){
        return ResponseEntity.ok(myPageService.getmyinfo(memberId));
    }
}
