package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.member.Update_AccountDTO_REQ;
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

    // 회원 본인 정보 불러오기
    @PostMapping("/mypage")
    public ResponseEntity<GetMyInfo_MyPageDTO_RES> getmyinfo(){
        return ResponseEntity.ok(myPageService.getmyinfo());
    }

    @PostMapping("/updateself")
    public ResponseEntity<UpdateSelf_MyPageDTO_RES> updateself(@RequestBody Update_AccountDTO_REQ updateAccountDTOReq){
        return ResponseEntity.ok(myPageService.updateself(updateAccountDTOReq));
    }
}
