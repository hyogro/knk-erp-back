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
    @GetMapping("")
    public ResponseEntity<GetMyInfo_MyPageDTO_RES> getMyInfo(){
        return ResponseEntity.ok(myPageService.getMyInfo());
    }

    // 회원 본인 정보 수정
    @PutMapping("")
    public ResponseEntity<UpdateSelf_MyPageDTO_RES> updateSelf(@RequestBody Update_AccountDTO_REQ updateAccountDTOReq){
        return ResponseEntity.ok(myPageService.updateSelf(updateAccountDTOReq));
    }

    // 본인 포상휴가 분단위
    @GetMapping("/getMyVacation")
    public ResponseEntity<GetMyVacation_MyPageDTO_RES> getMyVacation(){return ResponseEntity.ok(myPageService.getMyVacation());}
}
