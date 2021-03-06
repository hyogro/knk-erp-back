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
    public void ??????_??????1_??????() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("test01");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("????????????");
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setDepartmentId(1L);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(13)
    public void ??????_??????2_??????() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("test02");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("????????????");
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setDepartmentId(1L);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(14)
    public void ?????????_??????1_??????() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("testadmin01");
        memberDTOReq.setPassword("testadmin1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("????????????");
        memberDTOReq.setAuthority(Authority.ROLE_LVL3);
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setDepartmentId(1L);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(15)
    public void ?????????_??????2_??????() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("?????????");
        memberDTOReq.setMemberId("testadmin02");
        memberDTOReq.setPassword("testadmin1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("????????????");
        memberDTOReq.setAuthority(Authority.ROLE_LVL3);
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setDepartmentId(1L);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(20)
    @WithUserDetails("testadmin01")
    public void ?????????_????????????_??????_??????() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ();
        departmentDTOReq.setDepartmentName("???????????????");
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
    public void ??????_??????_??????() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("???????????????", false);

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
    public void ??????_??????_??????_??????_??????_??????() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("???????????????", false);

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
    public void ??????_??????_??????_??????_?????????_??????_??????() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("???????????????", false);

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
    public void ??????_??????_??????_??????() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("???????????????", false);

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
    public void ??????_??????_??????_??????_??????????????????_??????() throws Exception {
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
    public void ??????_??????_??????_??????_????????????_??????() throws Exception {
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
    public void ??????_??????_??????_??????() throws Exception {
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
    public void ??????_??????_??????_??????_?????????_????????????() throws Exception {
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
    public void ??????_??????_??????_??????() throws Exception {
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
    public void ??????_??????_????????????_??????() throws Exception {
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
    public void ??????_??????_??????_??????_??????_??????_?????????_????????????_??????_??????_??????() throws Exception {
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
    public void ??????_??????_??????_??????_??????_??????_?????????_????????????_??????() throws Exception {
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
    public void ????????????_?????????_?????????_???????????????_????????????_????????????_??????() throws Exception {
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
    public void ??????_????????????_??????() throws Exception {
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
    public void ?????????_??????_??????_??????() throws Exception {
        DepartmentDTO_REQ departmentDTOReq = new DepartmentDTO_REQ("???????????????", false);

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
    public void ??????_??????_??????_?????????_??????() throws Exception {
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
    public void ??????_??????_??????() throws Exception {
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
    public void ?????????_??????_??????() throws Exception {
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
    public void ?????????_??????_??????() throws Exception {
        MvcResult result = departmentMvc.perform(
                        MockMvcRequestBuilders.get("/department/readOrganizationChart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A1640");
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
