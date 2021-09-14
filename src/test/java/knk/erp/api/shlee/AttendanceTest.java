package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.service.AccountService;
import knk.erp.api.shlee.domain.account.service.DepartmentService;
import knk.erp.api.shlee.domain.schedule.controller.AttendanceController;
import knk.erp.api.shlee.domain.schedule.dto.Attendance.UuidDTO;
import knk.erp.api.shlee.domain.schedule.entity.Attendance;
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
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AttendanceTest {
    @Autowired
    private MockMvc attendanceMvc;
    @Autowired
    private AttendanceController attendanceController;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;
    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        attendanceMvc = MockMvcBuilders
                .standaloneSetup(attendanceController)
                .setControllerAdvice(new CustomControllerAdvice())
                .addFilter(new CharacterEncodingFilter("utf-8", true))
                .build();
    }

    @Test
    @Order(10)
    public void 테스트_정상동작_확인(){
        System.out.println("정상동작 확인.");
    }
    @Test
    @Order(11)
    public void 부서_생성(){
        departmentService.createDepartment(new DepartmentDTO_REQ("부서미지정", false));
    }

    @Test
    @Order(12)
    public void 맴버_생성(){
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test_id");
        memberDTOReq.setPassword("test_pw");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("테스터");
        memberDTOReq.setJoiningDate(LocalDate.now());
        accountService.signup(memberDTOReq);

    }

    @Test
    @Order(20)
    @WithUserDetails("test_id")
    public void 출근기록_성공() throws Exception {
        String testUuid = "just-test-uuid";
        UuidDTO uuidDTO = UuidDTO.builder().uuid(testUuid).build();

        String requestBody = objectMapper.writeValueAsString(uuidDTO);

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/onWork")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5501");
    }

    @Test
    @Order(21)
    @WithUserDetails("test_id")
    public void 출근기록_실패_하루에_두번출근은_안되지() throws Exception {
        String testUuid = "just-test-uuid";
        UuidDTO uuidDTO = UuidDTO.builder().uuid(testUuid).build();

        String requestBody = objectMapper.writeValueAsString(uuidDTO);

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/onWork")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E5501");
    }

    //응답코드 가져오기
    private String getCode(MvcResult result) throws UnsupportedEncodingException {
        System.out.println(">> response >> "+result.getResponse().getContentAsString());
        JSONObject j1 = new JSONObject(result.getResponse().getContentAsString());
        return j1.get("code").toString();
    }

    //응답객체 가져오기
    private JSONObject getResponse(MvcResult result) throws UnsupportedEncodingException {
        return new JSONObject(result.getResponse().getContentAsString());
    }


}
