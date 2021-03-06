package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.Evaluation.controller.EvaluationController;
import knk.erp.api.shlee.domain.Evaluation.dto.EvaluationDTO;
import knk.erp.api.shlee.domain.Evaluation.service.EvaluationService;
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
public class EvaluationTest {
    @Autowired
    private MockMvc evaluationMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EvaluationController evaluationController;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void beforeEach() {
        evaluationMvc = MockMvcBuilders
                .standaloneSetup(evaluationController)
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
    @Order(12)
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
    @Order(20)
    @WithUserDetails("testadmin01")
    public void ?????????_??????_?????????_??????() throws Exception {
        EvaluationDTO evaluationDTO = new EvaluationDTO("????????????");

        String requestBody = objectMapper.writeValueAsString(evaluationDTO);

        MvcResult result = evaluationMvc.perform(
                        MockMvcRequestBuilders.post("/evaluation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4000");
    }

    @Test
    @Order(21)
    @WithUserDetails("testadmin01")
    public void ?????????_??????_????????????_??????() throws Exception {
        MvcResult result = evaluationMvc.perform(
                        MockMvcRequestBuilders.get("/evaluation"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4001");
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
