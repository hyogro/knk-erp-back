package knk.erp.api.shlee.board.controller;

import knk.erp.api.shlee.board.dto.board.*;
import knk.erp.api.shlee.board.dto.boardlist.NoticeLatestDTO_RES;
import knk.erp.api.shlee.board.dto.boardlist.Read_NoticeBoardDTO_RES;
import knk.erp.api.shlee.board.dto.boardlist.Read_WorkBoardListDTO_RES;
import knk.erp.api.shlee.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("")
    public ResponseEntity<Create_BoardDTO_RES> createBoard(@RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.createBoard(boardDTO));
    }

    // 게시글 읽기
    @GetMapping("/{board_idx}")
    public ResponseEntity<Read_BoardDTO_RES> readBoard(@PathVariable Long board_idx){
        return ResponseEntity.ok(boardService.readBoard(board_idx));
    }

    // 게시글 수정
    @PutMapping("/{board_idx}")
    public ResponseEntity<Update_BoardDTO_RES> updateBoard(@PathVariable Long board_idx, @RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.updateBoard(board_idx, boardDTO));
    }

    // 게시글 삭제
    @DeleteMapping("/{board_idx}")
    public ResponseEntity<Delete_BoardDTO_RES> deleteBoard(@PathVariable Long board_idx){
        return ResponseEntity.ok(boardService.deleteBoard(board_idx));
    }

    // 업무게시판 목록 보기
    @GetMapping("/workBoardList")
    public ResponseEntity<Read_WorkBoardListDTO_RES> workBoardList(Pageable pageable,
                                                               @RequestParam("searchType") String searchType,
                                                               @RequestParam("keyword") String keyword){
        return ResponseEntity.ok(boardService.workBoardList(pageable, searchType, keyword));
    }

    // 공지사항 목록 보기
    @GetMapping("/noticeBoardList")
    public ResponseEntity<Read_NoticeBoardDTO_RES> noticeBoardList(Pageable pageable,
                                                                   @RequestParam("searchType") String searchType,
                                                                   @RequestParam("keyword") String keyword){
        return ResponseEntity.ok(boardService.noticeBoardList(pageable, searchType, keyword));
    }

    // 공지사항 최신순 5개 보기
    @GetMapping("/noticeLatest")
    public ResponseEntity<NoticeLatestDTO_RES> noticeLatest(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(boardService.noticeLatest(pageable));
    }

    // 멤버 id와 이름 목록 불러오기
    @GetMapping("/memberIdList")
    public ResponseEntity<MemberIdListDTO_RES> memberIdList(){
        return ResponseEntity.ok(boardService.memberIdList());
    }
}
