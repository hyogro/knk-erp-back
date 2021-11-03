package knk.erp.api.shlee.domain.board.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.board.dto.board.*;
import knk.erp.api.shlee.domain.board.dto.boardlist.*;
import knk.erp.api.shlee.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("")
    public ResponseEntity<ResponseData> createBoard(@RequestBody BoardDTO boardDTO){
        boardService.createBoard(boardDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_BOARD_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 게시글 읽기
    @GetMapping("/{board_idx}")
    public ResponseEntity<ResponseData> readBoard(@PathVariable Long board_idx){
        Read_BoardDTO_RES readBoardDTORes = boardService.readBoard(board_idx);
        System.out.println(1);
        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_DETAIL_BOARD_SUCCESS)
                .data(readBoardDTORes)
                .build();
        System.out.println(2);
        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/{board_idx}")
    public ResponseEntity<ResponseData> updateBoard(@PathVariable Long board_idx, @RequestBody BoardDTO boardDTO){
        boardService.updateBoard(board_idx, boardDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.UPDATE_BOARD_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{board_idx}")
    public ResponseEntity<ResponseData> deleteBoard(@PathVariable Long board_idx){
        boardService.deleteBoard(board_idx);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_BOARD_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 업무게시판 목록 보기
    @GetMapping("/workBoardList")
    public ResponseEntity<ResponseData> workBoardList(Pageable pageable,
                                                               @RequestParam("searchType") String searchType,
                                                               @RequestParam("keyword") String keyword){
        Read_BoardListDTO_RES readBoardListDTORes = boardService.workBoardList(pageable, searchType, keyword);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_WORK_BOARD_LIST_SUCCESS)
                .data(readBoardListDTORes)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 현장팀게시판 목록 보기
    @GetMapping("/fieldTeamBoardList")
    public ResponseEntity<ResponseData> fieldTeamBoardList(Pageable pageable,
                                                                             @RequestParam("searchType") String searchType,
                                                                             @RequestParam("keyword") String keyword){
        Read_BoardListDTO_RES readBoardListDTORes = boardService.fieldTeamBoardList(pageable, searchType, keyword);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_FIELD_TEAM_BOARD_LIST_SUCCESS)
                .data(readBoardListDTORes)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 공지사항 목록 보기
    @GetMapping("/noticeBoardList")
    public ResponseEntity<ResponseData> noticeBoardList(Pageable pageable,
                                                                   @RequestParam("searchType") String searchType,
                                                                   @RequestParam("keyword") String keyword){
        Read_BoardListDTO_RES readBoardListDTORes = boardService.noticeBoardList(pageable, searchType, keyword);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_NOTICE_BOARD_LIST_SUCCESS)
                .data(readBoardListDTORes)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 공지사항 최신순 5개 보기
    @GetMapping("/noticeLatest")
    public ResponseEntity<ResponseData> noticeLatest(@PageableDefault(size = 5) Pageable pageable){
        Page<BoardListDTO> page = boardService.noticeLatest(pageable);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_LATEST_NOTICE_BOARD_LIST_SUCCESS)
                .data(page)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 멤버 id와 이름 목록 불러오기
    @GetMapping("/memberIdList")
    public ResponseEntity<ResponseData> memberIdList() {
        List<Get_memberIdListDTO> memberIdList = boardService.memberIdList();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.GET_MEMBER_ID_LIST_SUCCESS)
                .data(memberIdList)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }
}
