package knk.erp.api.shlee;

import knk.erp.api.shlee.account.dto.account.Login_TokenDTO_RES;
import knk.erp.api.shlee.account.dto.account.SignUp_MemberDTO_RES;
import knk.erp.api.shlee.account.dto.member.MemberDTO_REQ;
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

import java.time.LocalDateTime;

@SpringBootTest
class ShleeApplicationTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AccountService accountService;
    @Autowired
    ScheduleService scheduleService;
    private String accessToken="";

    @Test
    @Order(1)
    @DisplayName("Member CRUD 통합테스트")
    public void contextLoads() {
        signUpTest();
        String accessToken = loginTest();
        readMemberTest();
        updateMemberTest(accessToken);
        deleteMemberTest(accessToken);
    }
    private void signUpTest(){
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ(null, "suTest_memberId_1", "1234"
                , "010-6828-3435", "테스트_1", 0, 0L, "", null);
        SignUp_MemberDTO_RES signUp_memberDTO_res = accountService.signup(memberDTOReq);
        logger.info("signUpTest result: {}", signUp_memberDTO_res);
    }

    private String loginTest(){
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ(null, "suTest_memberId_1", "1234"
                , null, null, 0, null, null, null);
        Login_TokenDTO_RES login_tokenDTO_res = accountService.login(memberDTOReq);
        logger.info("signUpTest result: {}", login_tokenDTO_res);
        return "Bearer " + login_tokenDTO_res.getTokenDto().getAccessToken();
    }

    private void readMemberTest(){
        logger.info("readMemberTest result: {}", accountService.readMember());
    }

    private void updateMemberTest(String accessToken){
        logger.info("updateMemberTest result: {}", accountService.readMember());
    }

    private void deleteMemberTest(String accessToken){
        logger.info("deleteMemberTest result: {}", accountService.readMember());
    }

    @Test
    @Order(2)
    @DisplayName("일정생성 단위테스트")
    public void createScheduleTest(){
        String memberId = "test1";
        Long departmentId = 0L;
        ScheduleDTO scheduleDTO = new ScheduleDTO(null, "csTest_title_1", "csTest_memo_1"
                , LocalDateTime.now(), LocalDateTime.now(), memberId, departmentId);

        logger.info("createScheduleTest result: {}", scheduleService.createSchedule(scheduleDTO));
    }




}
