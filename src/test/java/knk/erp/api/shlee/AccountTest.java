package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.account.controller.AccountController;
import knk.erp.api.shlee.domain.account.dto.account.Check_existMemberIdDTO;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.Update_AccountDTO_REQ;
import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.service.AccountService;
import knk.erp.api.shlee.domain.account.service.DepartmentService;
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
public class AccountTest {
    @Autowired
    private MockMvc accountMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountController accountController;
    @Autowired
    private AccountService accountService;

    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        accountMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .setControllerAdvice(new CustomControllerAdvice())
                .addFilter(new CharacterEncodingFilter("utf-8", true))
                .build();
    }

    @Test
    @Order(10)
    public void ?????????_????????????_??????() {
        System.out.println("???????????? ??????.");
    }

    @Test
    @Order(11)
    public void ???????????????_??????() {
        departmentService.createDepartment(new DepartmentDTO_REQ("???????????????", false));
    }

    @Test
    @Order(11)
    public void ???????????????_??????() {
        departmentService.createDepartment(new DepartmentDTO_REQ("???????????????", false));
    }

    @Test
    @Order(12)
    public void ??????_??????_??????() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("test01");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("?????????");
        memberDTOReq.setJoiningDate(LocalDate.now());
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(13)
    public void ?????????_??????_??????() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("testadmin01");
        memberDTOReq.setPassword("testadmin1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("?????????");
        memberDTOReq.setAuthority(Authority.ROLE_LVL3);
        memberDTOReq.setJoiningDate(LocalDate.now());
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(14)
    public void ??????_??????_??????() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("testceo01");
        memberDTOReq.setPassword("testceo1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("?????????");
        memberDTOReq.setAuthority(Authority.ROLE_LVL4);
        memberDTOReq.setJoiningDate(LocalDate.now());
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(20)
    @WithUserDetails("testadmin01")
    public void ?????????_????????????_????????????() throws Exception {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("test01");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("????????????");
        memberDTOReq.setAuthority(Authority.ROLE_LVL1);

        String requestBody = objectMapper.writeValueAsString(memberDTOReq);

        MvcResult result = accountMvc.perform(
                MockMvcRequestBuilders.post("/account/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1100");
    }

    @Test
    @Order(21)
    @WithUserDetails("testadmin01")
    public void ?????????????????????_??????????????????_????????????() throws Exception {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("????????????");
        memberDTOReq.setAuthority(Authority.ROLE_LVL1);

        String requestBody = objectMapper.writeValueAsString(memberDTOReq);

        MvcResult result = accountMvc.perform(
                        MockMvcRequestBuilders.post("/account/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9800");
    }

    @Test
    @Order(22)
    @WithUserDetails("testadmin01")
    public void ?????????????????????_????????????_????????????_?????????() throws Exception {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("test02");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("????????? ????????? ????????? ???????");
        memberDTOReq.setAuthority(Authority.ROLE_LVL1);

        String requestBody = objectMapper.writeValueAsString(memberDTOReq);

        MvcResult result = accountMvc.perform(
                        MockMvcRequestBuilders.post("/account/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9800");
    }

    @Test
    @Order(23)
    @WithUserDetails("testadmin01")
    public void ????????????_??????() throws Exception {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("test02");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("?????????");
        memberDTOReq.setAuthority(Authority.ROLE_LVL1);

        String requestBody = objectMapper.writeValueAsString(memberDTOReq);

        MvcResult result = accountMvc.perform(
                        MockMvcRequestBuilders.post("/account/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1000");
    }

    @Test
    @Order(30)
    @WithUserDetails("testadmin01")
    public void ?????????_??????_??????_????????????() throws Exception {
        Check_existMemberIdDTO checkExistMemberIdDTO = new Check_existMemberIdDTO();
        checkExistMemberIdDTO.setMemberId("test01");
        checkExistMemberIdDTO.setDummy(false);

        String requestBody = objectMapper.writeValueAsString(checkExistMemberIdDTO);

        MvcResult result = accountMvc.perform(
                MockMvcRequestBuilders.post("/account/checkId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1400");
    }

    @Test
    @Order(31)
    @WithUserDetails("testadmin01")
    public void ?????????_??????_??????_???????????????() throws Exception {
        Check_existMemberIdDTO checkExistMemberIdDTO = new Check_existMemberIdDTO();
        checkExistMemberIdDTO.setMemberId("test12");
        checkExistMemberIdDTO.setDummy(false);

        String requestBody = objectMapper.writeValueAsString(checkExistMemberIdDTO);

        MvcResult result = accountMvc.perform(
                        MockMvcRequestBuilders.post("/account/checkId")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1400");
    }

    @Test
    @Order(40)
    @WithUserDetails("testadmin01")
    public void ????????????_????????????_??????() throws Exception {
        MvcResult result = accountMvc.perform(
                MockMvcRequestBuilders.get("/account"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1100");
    }

    @Test
    @Order(41)
    @WithUserDetails("testadmin01")
    public void ????????????_????????????() throws Exception {
        MvcResult result = accountMvc.perform(
                MockMvcRequestBuilders.get("/account/"+"test01"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1110");
    }

    @Test
    @Order(50)
    @WithUserDetails("testadmin01")
    public void ????????????_??????_????????????_???????????????_??????() throws Exception {
        Update_AccountDTO_REQ updateAccountDTOReq = new Update_AccountDTO_REQ();
        updateAccountDTOReq.setPhone("010-0000-0000");

        String requestBody = objectMapper.writeValueAsString(updateAccountDTOReq);

        MvcResult result = accountMvc.perform(
                        MockMvcRequestBuilders.put("/account/"+"testceo01")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9900");
    }

    @Test
    @Order(51)
    @WithUserDetails("testadmin01")
    public void ????????????_?????????_?????????_?????????_?????????_??????() throws Exception {
        Update_AccountDTO_REQ updateAccountDTOReq = new Update_AccountDTO_REQ();
        updateAccountDTOReq.setPhone("010-0000-0000");
        updateAccountDTOReq.setAuthority("ROLE_ADMIN");

        String requestBody = objectMapper.writeValueAsString(updateAccountDTOReq);

        MvcResult result = accountMvc.perform(
                        MockMvcRequestBuilders.put("/account/"+"test01")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9900");
    }

    @Test
    @Order(52)
    @WithUserDetails("testadmin01")
    public void ????????????_??????_??????() throws Exception {
        Update_AccountDTO_REQ updateAccountDTOReq = new Update_AccountDTO_REQ();
        updateAccountDTOReq.setPhone("010-0000-0000");
        updateAccountDTOReq.setAuthority("ROLE_LVL2");

        String requestBody = objectMapper.writeValueAsString(updateAccountDTOReq);

        MvcResult result = accountMvc.perform(
                        MockMvcRequestBuilders.put("/account/"+"test01")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1200");
    }

    @Test
    @Order(60)
    @WithUserDetails("testadmin01")
    public void ??????_??????_?????????_?????????_?????????_?????????_??????() throws Exception {
        MvcResult result = accountMvc.perform(
                MockMvcRequestBuilders.delete("/account/"+"testceo01"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9900");
    }

    @Test
    @Order(60)
    @WithUserDetails("testadmin01")
    public void ??????_??????_??????() throws Exception {
        MvcResult result = accountMvc.perform(
                        MockMvcRequestBuilders.delete("/account/"+"test01"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1300");
    }



    //???????????? ????????????
    private String getCode(MvcResult result) throws UnsupportedEncodingException {
        System.out.println(">> response >> " + result.getResponse().getContentAsString());
        JSONObject j1 = new JSONObject(result.getResponse().getContentAsString());
        return j1.get("code").toString();
    }

    //???????????? ????????????
    private JSONObject getResponse(MvcResult result) throws UnsupportedEncodingException {
        return new JSONObject(result.getResponse().getContentAsString());
    }
}
