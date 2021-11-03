package knk.erp.api.shlee;

import com.fasterxml.jackson.databind.ObjectMapper;
import knk.erp.api.shlee.domain.account.dto.department.DepartmentDTO_REQ;
import knk.erp.api.shlee.domain.account.dto.member.MemberDTO_REQ;
import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.service.AccountService;
import knk.erp.api.shlee.domain.account.service.DepartmentService;
import knk.erp.api.shlee.domain.board.controller.BoardController;
import knk.erp.api.shlee.domain.board.dto.board.BoardDTO;
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
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class BoardTest {
    @Autowired
    private MockMvc boardMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardController boardController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        boardMvc = MockMvcBuilders
                .standaloneSetup(boardController)
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
    public void 사업부_멤버_생성() {
        MemberDTO_REQ memberDTOReq = new MemberDTO_REQ();
        memberDTOReq.setPosition("포지션");
        memberDTOReq.setMemberId("test02");
        memberDTOReq.setPassword("test1234");
        memberDTOReq.setPhone("010-1234-1234");
        memberDTOReq.setMemberName("매니지");
        memberDTOReq.setJoiningDate(LocalDate.now());
        memberDTOReq.setAuthority(Authority.ROLE_MANAGE);
        accountService.signup(memberDTOReq);
    }

    @Test
    @Order(14)
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
    public void 공지_게시글_생성_권한_부족() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardType("공지사항");
        boardDTO.setTitle("실패할것");
        boardDTO.setContent("실패한다니까?");

        String requestBody = objectMapper.writeValueAsString(boardDTO);

        MvcResult result = boardMvc.perform(
                MockMvcRequestBuilders.post("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9900");
    }

    @Test
    @Order(21)
    @WithUserDetails("test01")
    public void 현장팀_게시글_생성_권한_부족() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardType("현장팀게시판");
        boardDTO.setTitle("실패할걸");
        boardDTO.setContent("실패할듯");

        String requestBody = objectMapper.writeValueAsString(boardDTO);

        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.post("/board")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E9900");
    }

    @Test
    @Order(22)
    @WithUserDetails("test01")
    public void 업무_게시글_생성_성공() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardType("업무게시판");
        boardDTO.setTitle("업무게시글");
        boardDTO.setContent("업무업무업무");

        String requestBody = objectMapper.writeValueAsString(boardDTO);

        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.post("/board")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4500");
    }

    @Test
    @Order(23)
    @WithUserDetails("test02")
    public void 공지사항_생성_성공() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardType("공지사항");
        boardDTO.setTitle("공지사항");
        boardDTO.setContent("공지공지공지");

        String requestBody = objectMapper.writeValueAsString(boardDTO);

        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.post("/board")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4500");
    }

    @Test
    @Order(24)
    @WithUserDetails("testadmin01")
    public void 현장팀_게시글_생성_성공() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardType("현장팀게시판");
        boardDTO.setTitle("현장팀게시글");
        boardDTO.setContent("현장현장현장");

        String requestBody = objectMapper.writeValueAsString(boardDTO);

        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.post("/board")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4500");
    }

    @Test
    @Order(40)
    @WithUserDetails("testadmin01")
    public void 게시글_수정_실패_작성자가_아님() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle("업무 수정 실패");
        boardDTO.setContent("업무실패업무");

        String requestBody = objectMapper.writeValueAsString(boardDTO);

        MvcResult result = boardMvc.perform(
                MockMvcRequestBuilders.put("/board/"+1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E4501");
    }

    @Test
    @Order(41)
    @WithUserDetails("test01")
    public void 게시글_수정_성공() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle("업무 수정 성공");
        boardDTO.setContent("업무성공업무");

        String requestBody = objectMapper.writeValueAsString(boardDTO);

        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.put("/board/"+1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4520");
    }

    @Test
    @Order(50)
    @WithUserDetails("test01")
    public void 게시글_삭제_성공() throws Exception {
        MvcResult result = boardMvc.perform(
                MockMvcRequestBuilders.delete("/board/"+1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4530");
    }

    @Test
    @Order(51)
    @WithUserDetails("test01")
    public void 게시글_읽기_실패_없는_게시글() throws Exception {
        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.get("/board/"+1L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("E4500");
    }

    @Test
    @Order(60)
    @WithUserDetails("test01")
    public void 업무_게시판_읽기_성공() throws Exception {
        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.get("/board/workBoardList")
                                .param("searchType","")
                                .param("keyword",""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4540");
    }

    @Test
    @Order(61)
    @WithUserDetails("test01")
    public void 공지사항_읽기_성공() throws Exception {
        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.get("/board/noticeBoardList")
                                .param("searchType","")
                                .param("keyword",""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4542");
    }

    @Test
    @Order(62)
    @WithUserDetails("test01")
    public void 현장팀_게시판_읽기_성공() throws Exception {
        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.get("/board/fieldTeamBoardList")
                                .param("searchType","")
                                .param("keyword",""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4541");
    }

    @Test
    @Order(63)
    @WithUserDetails("test01")
    public void 공지사항_최신순_5개_읽기_성공() throws Exception {
        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.get("/board/noticeLatest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4543");
    }

    @Test
    @Order(64)
    @WithUserDetails("testadmin01")
    public void 멤버_아이디_리스트_가져오기_성공() throws Exception {
        MvcResult result = boardMvc.perform(
                        MockMvcRequestBuilders.get("/board/memberIdList"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String code = this.getCode(result);
        assertThat(code).isEqualTo("A4550");
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
