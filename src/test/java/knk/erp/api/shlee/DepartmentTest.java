package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.account.controller.DepartmentController;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentMemberDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.department.Update_DepartmentLeaderDTO_REQ;
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
public class DepartmentTest {
    @Autowired
    private MockMvc departmentMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentController departmentController;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void beforeEach() {
        departmentMvc = MockMvcBuilders
                .standaloneSetup(departmentController)
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
    public void 일반_멤버1_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test01");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("일레벨일");
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setDepartmentId(1L);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(13)
    public void 일반_멤버2_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test02");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("일레벨이");
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setDepartmentId(1L);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(14)
    public void 관리자_멤버1_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("testadmin01");
        memberDTOReq.setPassword("testadmin1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("삼레벨일");
        memberDTOReq.setAuthority(Authority.ROLE_LVL3);
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setDepartmentId(1L);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(15)
    public void 관리자_멤버2_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("testadmin02");
        memberDTOReq.setPassword("testadmin1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("삼레벨이");
        memberDTOReq.setAuthority(Authority.ROLE_LVL3);
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setDepartmentId(1L);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(20)
    @WithUserDetails("testadmin01")
    public void 중복된_이름으로_부서_생성() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ();
        departmentDTOReq.setDepartmentName("부서미지정");
        departmentDTOReq.setDummy(false);

        String requestBody = objectMapper.writeValueAsString(departmentDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.post("/department")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1601");
    }

    @Test
    @Order(21)
    @WithUserDetails("testadmin01")
    public void 부서_생성_성공() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("테스트부서", false);

        String requestBody = objectMapper.writeValueAsString(departmentDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.post("/department")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1600");
    }

    @Test
    @Order(30)
    @WithUserDetails("testadmin01")
    public void 부서_이름_수정_실패_없는_부서() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("미지정부서", false);

        String requestBody = objectMapper.writeValueAsString(departmentDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.put("/department/"+25L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1600");
    }

    @Test
    @Order(31)
    @WithUserDetails("testadmin01")
    public void 부서_이름_수정_실패_중복된_부서_이름() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("테스트부서", false);

        String requestBody = objectMapper.writeValueAsString(departmentDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.put("/department/"+1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1601");
    }

    @Test
    @Order(32)
    @WithUserDetails("testadmin01")
    public void 부서_이름_수정_성공() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("미지정부서", false);

        String requestBody = objectMapper.writeValueAsString(departmentDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.put("/department/"+1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1620");
    }

    @Test
    @Order(33)
    @WithUserDetails("testadmin01")
    public void 부서_리더_수정_실패_존재하지않는_멤버() throws Exception {
        Update_DepartmentLeaderDTO_REQ updateDepartmentLeaderDTOReq = new Update_DepartmentLeaderDTO_REQ("notFound01",false);

        String requestBody = objectMapper.writeValueAsString(updateDepartmentLeaderDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.put("/department/updateLeader/"+1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1000");
    }

    @Test
    @Order(34)
    @WithUserDetails("testadmin01")
    public void 부서_리더_수정_실패_부서원이_아님() throws Exception {
        Update_DepartmentLeaderDTO_REQ updateDepartmentLeaderDTOReq = new Update_DepartmentLeaderDTO_REQ("testadmin02",false);

        String requestBody = objectMapper.writeValueAsString(updateDepartmentLeaderDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.put("/department/updateLeader/"+6L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1602");
    }

    @Test
    @Order(35)
    @WithUserDetails("testadmin01")
    public void 부서_리더_수정_성공() throws Exception {
        Update_DepartmentLeaderDTO_REQ updateDepartmentLeaderDTOReq = new Update_DepartmentLeaderDTO_REQ("testadmin02",false);

        String requestBody = objectMapper.writeValueAsString(updateDepartmentLeaderDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.put("/department/updateLeader/"+1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1621");
    }

    @Test
    @Order(36)
    @WithUserDetails("testadmin01")
    public void 부서_멤버_추가_실패_대상이_리더여서() throws Exception {
        DepartmentMemberDTO_REQ departmentMemberDTOReq = new DepartmentMemberDTO_REQ("testadmin02", false);

        String requestBody = objectMapper.writeValueAsString(departmentMemberDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.put("/department/addMember/"+6L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1200");
    }

    @Test
    @Order(37)
    @WithUserDetails("testadmin01")
    public void 부서_멤버_추가_성공() throws Exception {
        DepartmentMemberDTO_REQ departmentMemberDTOReq = new DepartmentMemberDTO_REQ("test01", false);

        String requestBody = objectMapper.writeValueAsString(departmentMemberDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.put("/department/addMember/"+6L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1622");
    }

    @Test
    @Order(40)
    @WithUserDetails("testadmin01")
    public void 부서_목록_읽어오기_성공() throws Exception {
        MvcResult result = departmentMvc.perform(
                MockMvcRequestBuilders.get("/department"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1610");
    }

    @Test
    @Order(41)
    @WithUserDetails("testadmin01")
    public void 선택_부서_외의_다른_부서_멤버_리스트_가져오기_실패_없는_부서() throws Exception {
        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.get("/department/readNotThisDepartmentMember/"+10L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1600");
    }

    @Test
    @Order(42)
    @WithUserDetails("testadmin01")
    public void 선택_부서_외의_다른_부서_멤버_리스트_가져오기_성공() throws Exception {
        MvcResult result = departmentMvc.perform(
                MockMvcRequestBuilders.get("/department/readNotThisDepartmentMember/"+6L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1612");
    }

    @Test
    @Order(43)
    @WithUserDetails("testadmin01")
    public void 로그인한_계정이_소속된_부서이름과_부서인원_가져오기_성공() throws Exception {
        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.get("/department/readDepartmentNameAndMemberCount"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1613");
    }

    @Test
    @Order(44)
    @WithUserDetails("testadmin01")
    public void 부서_상세보기_성공() throws Exception {
        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.get("/department/"+6L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1611");
    }

    @Test
    @Order(49)
    @WithUserDetails("testadmin01")
    public void 삭제할_부서_생성_성공() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("삭제할부서", false);

        String requestBody = objectMapper.writeValueAsString(departmentDTOReq);

        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.post("/department")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1600");
    }

    @Test
    @Order(50)
    @WithUserDetails("testadmin01")
    public void 부서_삭제_실패_부서원_존재() throws Exception {
        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.delete("/department/"+6L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E1603");
    }

    @Test
    @Order(51)
    @WithUserDetails("testadmin01")
    public void 부서_삭제_성공() throws Exception {
        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.delete("/department/"+7L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1630");
    }

    @Test
    @Order(52)
    @WithUserDetails("testadmin01")
    public void 부서원_삭제_성공() throws Exception {
        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.delete("/department/deleteMember/"+"test01"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1631");
    }

    @Test
    @Order(60)
    @WithUserDetails("testadmin01")
    public void 조직도_읽기_성공() throws Exception {
        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.get("/department/readOrganizationChart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1640");
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
