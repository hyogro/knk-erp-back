package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.account.controller.MyPageController;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.my.Update_SelfDTO;
import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.service.AccountService;
import knk.erp.api.shlee.domain.account.service.DepartmentService;
import knk.erp.api.shlee.domain.account.service.MyPageService;
import knk.erp.api.shlee.exception.component.CustomControllerAdvice;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class MyPageTest {
    @Autowired
    private MockMvc myPageMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MyPageController myPageController;

    @Autowired
    private AccountService accountService;
    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        myPageMvc = MockMvcBuilders
                .standaloneSetup(myPageController)
                .setControllerAdvice(new CustomControllerAdvice())
                .addFilter(new CharacterEncodingFilter("utf-8", true))
                .build();
    }

    @Test
    @Order(10)
    public void 테스트_정상동작_확인() {
        System.out.println("정상동작 확인.");
    }

    @Test
    @Order(11)
    public void 미지정부서_생성() {
        departmentService.createDepartment(new DepartmentDTO_REQ("부서미지정", false));
    }

    @Test
    @Order(11)
    public void 테스트부서_생성() {
        departmentService.createDepartment(new DepartmentDTO_REQ("테스트부서", false));
    }

    @Test
    @Order(12)
    public void 관리자_멤버_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("testadmin01");
        memberDTOReq.setPassword("testadmin1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("레벨삼");
        memberDTOReq.setAuthority(Authority.ROLE_LVL3);
        memberDTOReq.setJoiningDate(LocalDate.now());
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(20)
    @WithUserDetails("testadmin01")
    public void 내_정보_보기_성공() throws Exception {
        MvcResult result = myPageMvc.perform(
                MockMvcRequestBuilders.get("/my"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1500");
    }

    @Test
    @Order(30)
    @WithUserDetails("testadmin01")
    public void 내_정보_수정_성공() throws Exception {
        Update_SelfDTO updateSelfDTO = new Update_SelfDTO();
        updateSelfDTO.setAddress("전주시");
        updateSelfDTO.setPhone("010-1111-1111");

        String requestBody = objectMapper.writeValueAsString(updateSelfDTO);

        MvcResult result = myPageMvc.perform(
                MockMvcRequestBuilders.put("/my")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1520");
    }

    @Test
    @Order(40)
    @WithUserDetails("testadmin01")
    public void 내_추가_연차_보기_성공() throws Exception {
        MvcResult result = myPageMvc.perform(
                        MockMvcRequestBuilders.get("/my/getMyVacation"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1510");
    }



    //응답코드 가져오기
    private String getCode(MvcResult result) throws UnsupportedEncodingException {
        System.out.println(">> response >> " + result.getResponse().getContentAsString());
        JSONObject j1 = new JSONObject(result.getResponse().getContentAsString());
        return j1.get("code").toString();
    }

    //응답객체 가져오기
    private JSONObject getResponse(MvcResult result) throws UnsupportedEncodingException {
        return new JSONObject(result.getResponse().getContentAsString());
    }
}
