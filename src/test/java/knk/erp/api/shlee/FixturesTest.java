package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.Fixtures.controller.FixturesFormController;
import knk.erp.api.shlee.domain.Fixtures.dto.*;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.service.AccountService;
import knk.erp.api.shlee.domain.account.service.DepartmentService;
import knk.erp.api.shlee.exception.component.CustomControllerAdvice;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class FixturesTest {
    @Autowired
    private MockMvc fixturesMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FixturesFormController fixturesFormController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        fixturesMvc = MockMvcBuilders
                .standaloneSetup(fixturesFormController)
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
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
    public void 사업부_멤버_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test01");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("레벨일");
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setAuthority(Authority.ROLE_MANAGE);
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
    @Order(20)
    @WithUserDetails("test01")
    public void 비품_요청서1_생성_성공() throws Exception {
        FixturesFormDTO_REQ fixturesFormDTO_req = new FixturesFormDTO_REQ();
        List<FixturesDTO_REQ> fixturesDTOReqs = new ArrayList<>();
        fixturesDTOReqs.add(new FixturesDTO_REQ("비품1", 1, "1개"));
        fixturesDTOReqs.add(new FixturesDTO_REQ("비품2", 2, "2개"));
        fixturesDTOReqs.add(new FixturesDTO_REQ("비품3", 3, "3개"));
        fixturesFormDTO_req.setFixturesDTOReq(fixturesDTOReqs);

        String requestBody = objectMapper.writeValueAsString(fixturesFormDTO_req);

        MvcResult result = fixturesMvc.perform(
                MockMvcRequestBuilders.post("/fixtures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4100");
    }

    @Test
    @Order(21)
    @WithUserDetails("test01")
    public void 비품_요청서2_생성_성공() throws Exception {
        FixturesFormDTO_REQ fixturesFormDTO_req = new FixturesFormDTO_REQ();
        List<FixturesDTO_REQ> fixturesDTOReqs = new ArrayList<>();
        fixturesDTOReqs.add(new FixturesDTO_REQ("비품4", 4, "4개"));
        fixturesDTOReqs.add(new FixturesDTO_REQ("비품5", 5, "5개"));
        fixturesDTOReqs.add(new FixturesDTO_REQ("비품6", 6, "6개"));
        fixturesFormDTO_req.setFixturesDTOReq(fixturesDTOReqs);

        String requestBody = objectMapper.writeValueAsString(fixturesFormDTO_req);

        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.post("/fixtures")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4100");
    }

    @Test
    @Order(22)
    @WithUserDetails("test01")
    public void 내_비품_요청서_리스트_읽기() throws Exception {
        MvcResult result = fixturesMvc.perform(
                MockMvcRequestBuilders.get("/fixtures"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4110");
    }

    @Test
    @Order(23)
    @WithUserDetails("test01")
    public void 비품_요청서_상세보기() throws Exception {
        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.get("/fixtures/"+5L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4120");
    }

    @Test
    @Order(24)
    @WithUserDetails("testadmin01")
    public void 비품_요청서_수정_실패_작성자가_아님() throws Exception {
        Update_FixturesFormDTO_REQ updateFixturesFormDTOReq = new Update_FixturesFormDTO_REQ();
        List<Update_FixturesDTO_REQ> updateFixturesDTOReqs = new ArrayList<>();
        updateFixturesDTOReqs.add(new Update_FixturesDTO_REQ("비품7", 7, "7개"));
        updateFixturesDTOReqs.add(new Update_FixturesDTO_REQ("비품7", 7, "7개"));
        updateFixturesFormDTOReq.setUpdateFixturesDTOReq(updateFixturesDTOReqs);

        String requestBody = objectMapper.writeValueAsString(updateFixturesFormDTOReq);

        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.put("/fixtures/"+9L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E4101");
    }

    @Test
    @Order(25)
    @WithUserDetails("test01")
    public void 비품_요청서_수정_성공() throws Exception {
        Update_FixturesFormDTO_REQ updateFixturesFormDTOReq = new Update_FixturesFormDTO_REQ();
        List<Update_FixturesDTO_REQ> updateFixturesDTOReqs = new ArrayList<>();
        updateFixturesDTOReqs.add(new Update_FixturesDTO_REQ("비품7", 7, "7개"));
        updateFixturesDTOReqs.add(new Update_FixturesDTO_REQ("비품8", 8, "8개"));
        updateFixturesFormDTOReq.setUpdateFixturesDTOReq(updateFixturesDTOReqs);

        String requestBody = objectMapper.writeValueAsString(updateFixturesFormDTOReq);

        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.put("/fixtures/"+9L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4111");
    }

    @Test
    @Order(30)
    @WithUserDetails("testadmin01")
    public void 전체_비품_요청서_리스트_읽기() throws Exception {

        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.get("/fixtures/listAll")
                                .param("searchType", "승인미처리"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4130");
    }

    @Test
    @Order(31)
    @WithUserDetails("testadmin01")
    public void 비품_요청_승인_실패_없는_요청서() throws Exception {
        Confirm_FixturesDTO confirmFixturesDTO = new Confirm_FixturesDTO();
        List<Long> fixturesId = new ArrayList<>();
        fixturesId.add(10L);
        confirmFixturesDTO.setFixturesId(fixturesId);
        confirmFixturesDTO.setConfirm(true);

        String requestBody = objectMapper.writeValueAsString(confirmFixturesDTO);

        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.put("/fixtures/confirm/"+40L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E4100");
    }

    @Test
    @Order(32)
    @WithUserDetails("testadmin01")
    public void 비품_요청_승인_실패_없는_요청_항목() throws Exception {
        Confirm_FixturesDTO confirmFixturesDTO = new Confirm_FixturesDTO();
        List<Long> fixturesId = new ArrayList<>();
        fixturesId.add(80L);
        confirmFixturesDTO.setFixturesId(fixturesId);
        confirmFixturesDTO.setConfirm(true);

        String requestBody = objectMapper.writeValueAsString(confirmFixturesDTO);

        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.put("/fixtures/confirm/"+5L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E4102");
    }

    @Test
    @Order(33)
    @WithUserDetails("testadmin01")
    public void 비품_요청_승인_성공() throws Exception {
        Confirm_FixturesDTO confirmFixturesDTO = new Confirm_FixturesDTO();
        List<Long> fixturesId = new ArrayList<>();
        fixturesId.add(6L);
        confirmFixturesDTO.setFixturesId(fixturesId);
        confirmFixturesDTO.setConfirm(true);

        String requestBody = objectMapper.writeValueAsString(confirmFixturesDTO);

        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.put("/fixtures/confirm/"+5L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4140");
    }

    @Test
    @Order(34)
    @WithUserDetails("testadmin01")
    public void 비품_구매여부_변경_성공() throws Exception {
        Purchase_FixturesDTO pruchaseFixturesDTO = new Purchase_FixturesDTO();
        List<Long> fixturesId = new ArrayList<>();
        fixturesId.add(6L);
        pruchaseFixturesDTO.setFixturesId(fixturesId);
        pruchaseFixturesDTO.setPurchase(true);

        String requestBody = objectMapper.writeValueAsString(pruchaseFixturesDTO);

        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.put("/fixtures/purchase/"+5L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4141");
    }

    @Test
    @Order(40)
    @WithUserDetails("test01")
    public void 내_비품_요청서_삭제_실패_이미_처리됨() throws Exception {
        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.delete("/fixtures/"+5L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E4110");
    }

    @Test
    @Order(41)
    @WithUserDetails("test01")
    public void 내_비품_요청서_삭제_성공() throws Exception {
        MvcResult result = fixturesMvc.perform(
                        MockMvcRequestBuilders.delete("/fixtures/"+9L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4112");
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
