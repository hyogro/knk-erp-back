package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.account.controller.AccountController;
import knk.erp.api.shlee.domain.account.dto.account.Check_existMemberIdDTO;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.entity.DepartmentRepository;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.domain.account.service.AccountService;
import knk.erp.api.shlee.domain.account.service.DepartmentService;
import knk.erp.api.shlee.exception.component.CustomControllerAdvice;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountController accountController;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void beforeEach() {
        accountMvc = MockMvcBuilders
                .standaloneSetup(accountMvc)
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
    public void 일반_멤버_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test01");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("레벨일");
        memberDTOReq.setJoiningDate(LocalDate.now());
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(13)
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
    @Order(14)
    public void 대표_멤버_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("testceo01");
        memberDTOReq.setPassword("testceo1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("레벨사");
        memberDTOReq.setAuthority(Authority.ROLE_LVL4);
        memberDTOReq.setJoiningDate(LocalDate.now());
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(20)
    @WithUserDetails("testadmin01")
    public void 중복된_아이디로_계정생성() throws Exception {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test01");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("오류날걸");
        memberDTOReq.setAuthority(Authority.ROLE_LVL1);
        memberDTOReq.setJoiningDate(LocalDate.now());

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
    public void 계정생성하는데_필수입력정보_입력안함() throws Exception {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("오류날걸");
        memberDTOReq.setAuthority(Authority.ROLE_LVL1);
        memberDTOReq.setJoiningDate(LocalDate.now());

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
    public void 계정생성하는데_입력정보_길이제한_벗어남() throws Exception {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test02");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("길이가 길어서 오류가 날걸?");
        memberDTOReq.setAuthority(Authority.ROLE_LVL1);
        memberDTOReq.setJoiningDate(LocalDate.now());

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
    public void 계정생성_성공() throws Exception {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test02");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("성공함");
        memberDTOReq.setAuthority(Authority.ROLE_LVL1);
        memberDTOReq.setJoiningDate(LocalDate.now());

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
    public void 아이디_중복_체크_중복일때() throws Exception {
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

        boolean check = this.getResponse(result).getJSONObject("data").getBoolean("check");
        assertThat(check).isTrue();
    }

    @Test
    @Order(31)
    @WithUserDetails("testadmin01")
    public void 아이디_중복_체크_중복아닐때() throws Exception {
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

        boolean check = this.getResponse(result).getJSONObject("data").getBoolean("check");
        assertThat(check).isFalse();
    }

    @Test
    @Order(40)
    @WithUserDetails("testadmin01")
    public void 회원목록_읽어오기_성공() throws Exception {
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
    public void 회원정보_상세보기() throws Exception {
        MvcResult result = accountMvc.perform(
                MockMvcRequestBuilders.get("/account/"+"test01"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1110");
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
