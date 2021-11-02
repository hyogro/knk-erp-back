package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.Evaluation.controller.EvaluationController;
import knk.erp.api.shlee.domain.Evaluation.dto.EvaluationDTO;
import knk.erp.api.shlee.domain.Materials.controller.MaterialsController;
import knk.erp.api.shlee.domain.Materials.dto.MaterialsDTO;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class MaterialsTest {
    @Autowired
    private MockMvc materialsMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MaterialsController materialsController;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void beforeEach() {
        materialsMvc = MockMvcBuilders
                .standaloneSetup(materialsController)
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
    public void 자재현황_파일_가져오기_실패_데이터_없음() throws Exception {
        MvcResult result = materialsMvc.perform(
                        MockMvcRequestBuilders.get("/materials"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E4010");
    }

    @Test
    @Order(21)
    @WithUserDetails("testadmin01")
    public void 자재현황_파일_업로드_성공() throws Exception {
        List<String> materials = new ArrayList<>();
        materials.add("자재");
        materials.add("현황");
        MaterialsDTO materialsDTO = new MaterialsDTO(materials);

        String requestBody = objectMapper.writeValueAsString(materialsDTO);

        MvcResult result = materialsMvc.perform(
                        MockMvcRequestBuilders.post("/materials")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4010");
    }

    @Test
    @Order(22)
    @WithUserDetails("testadmin01")
    public void 자재현황_파일_가져오기_성공() throws Exception {
        MvcResult result = materialsMvc.perform(
                        MockMvcRequestBuilders.get("/materials"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4011");
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
