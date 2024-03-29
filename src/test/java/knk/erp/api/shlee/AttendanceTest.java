package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.common.util.EntityUtil;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.domain.account.service.AccountService;
import knk.erp.api.shlee.domain.account.service.DepartmentService;
import knk.erp.api.shlee.domain.schedule.controller.AttendanceController;
import knk.erp.api.shlee.domain.schedule.dto.Attendance.RectifyAttendanceDTO;
import knk.erp.api.shlee.domain.schedule.entity.Attendance;
import knk.erp.api.shlee.domain.schedule.repository.AttendanceRepository;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private MemberRepository memberRepository;

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
    public void 테스트_정상동작_확인() {
        System.out.println("정상동작 확인.");
    }

    @Test
    @Order(11)
    public void 부서_생성() {
        departmentService.createDepartment(new DepartmentDTO_REQ("부서미지정", false));
    }

    @Test
    @Order(12)
    public void 일반_맴버_생성() {
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
    @Order(12)
    public void 관리자_맴버_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test_admin_id");
        memberDTOReq.setPassword("test_admin_pw");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("테스터");
        memberDTOReq.setAuthority(Authority.ROLE_LVL3);
        memberDTOReq.setJoiningDate(LocalDate.now());
        accountService.signup(memberDTOReq);
    }


    @Test
    @Order(20)
    @WithUserDetails("test_id")
    public void 출근_하지도_않고_퇴근찍으면_안되지() throws Exception {

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/offWork"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E5502");
    }

    @Test
    @Order(21)
    @WithUserDetails("test_id")
    public void 출근기록_성공() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", "just-test-uuid");

        String requestBody = objectMapper.writeValueAsString(map);

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
    @Order(22)
    @WithUserDetails("test_id")
    public void 하루에_두번출근은_안되지() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", "just-test-uuid");

        String requestBody = objectMapper.writeValueAsString(map);

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/onWork")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E5501");
    }


    @Test
    @Order(23)
    @WithUserDetails("test_id")
    public void 퇴근기록_성공() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/offWork"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5502");
    }

    @Test
    @Order(24)
    @WithUserDetails("test_id")
    public void 퇴근기록을_두번찍으면안되지() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/offWork"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E5505");
    }

    @Test
    @Order(30)
    @WithUserDetails("test_id")
    public void 출퇴근정보_10일치만_넣어볼게() {//10월 1 ~ 11일
        List<Attendance> attendanceList = new ArrayList<>();
        Member member = EntityUtil.getInstance().getMember(memberRepository);

        LocalTime nine = LocalTime.of(9,0);
        LocalTime six = LocalTime.of(18,0);
        for (int i = 10; i < 20; i++) {
            LocalDate date = LocalDate.of(2021, 9, i);
            Attendance attendance = Attendance
                    .builder()
                    .author(member)
                    .attendanceDate(date)
                    .onWork(nine)
                    .offWork(six)
                    .uuid("just-test-uuid")
                    .build();
            attendanceList.add(attendance);
        }
        attendanceRepository.saveAll(attendanceList);
    }
    @Test
    @Order(31)
    @WithUserDetails("test_id")
    public void 출퇴근정보_10일치_넣은거중에_5개만_조회해볼게() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("startDate","2021-09-11");
        map.add("endDate","2021-09-15");

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.get("/attendance/list").params(map))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5503");

        JSONObject response = getResponse(result);
        assertThat(response.getJSONArray("data").length()).isEqualTo(5);

    }

    @Test
    @Order(32)
    @WithUserDetails("test_id")
    public void 출퇴근정보_있는거_조회() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.get("/attendance/"+3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5503");
    }

    @Test
    @Order(40)
    @WithUserDetails("test_id")
    public void 정정요청생성_실패_이미_해당일에_출퇴근정보_있음() throws Exception {

        LocalDate date = LocalDate.of(2021,9, 10);
        LocalTime nine = LocalTime.of(9,0);
        LocalTime six = LocalTime.of(18,0);

        RectifyAttendanceDTO rectifyAttendanceDTO = RectifyAttendanceDTO
                .builder()
                .attendanceDate(date)
                .onWork(nine)
                .offWork(six)
                .memo("늦게왔어용_테스트")
                .build();

        String requestBody = objectMapper.writeValueAsString(rectifyAttendanceDTO);

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/rectify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();


        String code = this.getCode(result);
        assertThat(code).isEqualTo("E5501");
    }

    @Test
    @Order(41)
    @WithUserDetails("test_id")
    public void 정정요청생성_성공() throws Exception {

        LocalDate date = LocalDate.of(2021,9, 25);
        LocalTime nine = LocalTime.of(9,0);
        LocalTime six = LocalTime.of(18,0);

        RectifyAttendanceDTO rectifyAttendanceDTO = RectifyAttendanceDTO
                .builder()
                .attendanceDate(date)
                .onWork(nine)
                .offWork(six)
                .memo("늦게왔어용_테스트")
                .build();

        String requestBody = objectMapper.writeValueAsString(rectifyAttendanceDTO);

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/rectify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5504");
    }

    @Test
    @Order(42)
    @WithUserDetails("test_id")
    public void 정정요청생성_실패_이미_해당일에_정정요청_있음() throws Exception {

        LocalDate date = LocalDate.of(2021,9, 25);
        LocalTime nine = LocalTime.of(9,0);
        LocalTime six = LocalTime.of(18,0);

        RectifyAttendanceDTO rectifyAttendanceDTO = RectifyAttendanceDTO
                .builder()
                .attendanceDate(date)
                .onWork(nine)
                .offWork(six)
                .memo("늦게왔어용_테스트")
                .build();

        String requestBody = objectMapper.writeValueAsString(rectifyAttendanceDTO);

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/rectify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();


        String code = this.getCode(result);
        assertThat(code).isEqualTo("E5503");
    }

    @Test
    @Order(43)
    @WithUserDetails("test_id")
    public void 정정요청생성_성공_기존출근정보_수정() throws Exception {

        LocalTime nine = LocalTime.of(9,0);
        LocalTime six = LocalTime.of(21,0);

        RectifyAttendanceDTO rectifyAttendanceDTO = RectifyAttendanceDTO
                .builder()
                .onWork(nine)
                .offWork(six)
                .memo("늦게왔어용_테스트")
                .build();

        String requestBody = objectMapper.writeValueAsString(rectifyAttendanceDTO);

        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.post("/attendance/rectify/"+3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5504");
    }

    @Test
    @Order(50)
    @WithUserDetails("test_id")
    public void 정정요청_목록_조회성공() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.get("/attendance/rectify"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5505");

        JSONObject response = getResponse(result);
        assertThat(response.getJSONArray("data").length()).isEqualTo(2);

    }

    @Test
    @Order(51)
    @WithUserDetails("test_id")
    public void 정정요청_상세조회_실패_없는아이디() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.get("/attendance/rectify/"+99))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9100");
    }

    @Test
    @Order(51)
    @WithUserDetails("test_id")
    public void 정정요청_상세조회_성공() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.get("/attendance/rectify/"+5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5505");
    }

    @Test
    @Order(60)
    @WithUserDetails("test_id")
    public void 정정요청_삭제_성공() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.delete("/attendance/rectify/"+5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5506");
    }

    @Test
    @Order(70)
    @WithUserDetails("test_id")
    public void 승인대기정정요청_목록조회_실패_권한실패() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.get("/attendance/rectify/approve"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9900");
    }

    @Test
    @Order(71)
    @WithUserDetails("test_admin_id")
    public void 승인대기정정요청_목록조회_성공() throws Exception {
        MvcResult result = attendanceMvc.perform(
                MockMvcRequestBuilders.get("/attendance/rectify/approve"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A5505");

        JSONObject response = getResponse(result);
        assertThat(response.getJSONArray("data").length()).isEqualTo(1);
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
