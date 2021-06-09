package knk.erp.api.shlee;

import knk.erp.api.shlee.account.dto.account.Login_TokenDTO_RES;
import knk.erp.api.shlee.account.dto.account.SignUp_MemberDTO_RES;
import knk.erp.api.shlee.account.dto.account.Update_AccountDTO_RES;
import knk.erp.api.shlee.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.account.service.AccountService;
import knk.erp.api.shlee.schedule.dto.Schedule.ScheduleDTO;
import knk.erp.api.shlee.schedule.service.ScheduleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
class ShleeApplicationTests {
    //@Autowired
    //AccountService accountService;
    //@Autowired
    //ScheduleService scheduleService;
    //private String accessToken="";

    @Test
    @Order(1)
    public void contextLoads() {
        /*
        signUpTest();
        String accessToken = loginTest();
        readMemberTest();
        updateMemberTest(accessToken);
        deleteMemberTest(accessToken);
        */
    }
    /*
    private void signUpTest(){
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ(null, "suTest_memberId_1", "1234"
                , "010-6828-3435", "테스트_1", 0, 0L);
        SignUp_MemberDTO_RES signUp_memberDTO_res = accountService.signup(memberDTOReq);
        logger.info("맴버생성 테스트 result: {}", signUp_memberDTO_res);
    }

    private String loginTest(){
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ(null, "suTest_memberId_1", "1234"
                , null, null, 0, null);
        Login_TokenDTO_RES login_tokenDTO_res = accountService.login(memberDTOReq);
        logger.info("맴버로그인 테스트 result: {}", login_tokenDTO_res);
        return login_tokenDTO_res.getTokenDto().getAccessToken();
    }

    private void readMemberTest(){
        logger.info("맴버읽기 테스트 result: {}", accountService.readMember());
    }

    private void updateMemberTest(String accessToken){
        Update_AccountDTO_REQ memberDTOReq = new Update_AccountDTO_REQ(null, "suTest_memberId_1", "1234", "ROLE_LVL1"
                , "010-1234-1234", 0, 0L);
        Update_AccountDTO_RES updateMember = accountService.updateMember(memberDTOReq, accessToken);

        logger.info("맴버수정 테스트 result: {}", updateMember);
    }

    private void deleteMemberTest(String accessToken){
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ(null, "suTest_memberId_1", null
                , null, null, 0, null);
        logger.info("맴버삭제 테스트 result: {}", accountService.deleteMember(memberDTOReq, accessToken));
    }

    @Test
    @Order(2)
    public void createScheduleTest(){
        String memberId = "test1";
        Long departmentId = 0L;
        ScheduleDTO scheduleDTO = new ScheduleDTO(null, "csTest_title_1", "csTest_memo_1"
                , LocalDateTime.now(), LocalDateTime.now(), memberId, departmentId);

        logger.info("일정생성 테스트 result: {}", scheduleService.createSchedule(scheduleDTO));
    }

*/


}
