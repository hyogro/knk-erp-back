package knk.erp.api.shlee.board.controller;

import knk.erp.api.shlee.board.dto.board.*;
import knk.erp.api.shlee.board.dto.boardlist.BoardListSearchDTO_REQ;
import knk.erp.api.shlee.board.dto.boardlist.NoticeListDTO_RES;
import knk.erp.api.shlee.board.dto.boardlist.Search_BoardListDTO_RES;
import knk.erp.api.shlee.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("/createBoard")
    public ResponseEntity<Create_BoardDTO_RES> createBoard(@RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.createBoard(boardDTO));
    }

    // 게시글 읽기
    @PostMapping("/{board_idx}")
    public ResponseEntity<Read_BoardDTO_RES> readBoard(@PathVariable Long board_idx, @RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.readBoard(boardDTO));
    }

    // 게시글 수정
    @PostMapping("/{board_idx}/updateBoard")
    public ResponseEntity<Update_BoardDTO_RES> updateBoard(@PathVariable Long board_idx, @RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.updateBoard(boardDTO));
    }

    // 게시글 삭제
    @PostMapping("/{board_idx}/deleteBoard")
    public ResponseEntity<Delete_BoardDTO_RES> deleteBoard(@PathVariable Long board_idx, @RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.deleteBoard(boardDTO));
    }

    // 게시글 목록 보기
    @PostMapping("/list")
    public ResponseEntity<Search_BoardListDTO_RES> boardList(@PageableDefault Pageable pageable,
                                                             @RequestBody BoardListSearchDTO_REQ boardListSearchDTOReq){
        return ResponseEntity.ok(boardService.boardList(pageable, boardListSearchDTOReq));
    }

    // 공지사항 최신순 5개 보기
    @PostMapping("/noticeList")
    public ResponseEntity<NoticeListDTO_RES> noticeList(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(boardService.noticeList(pageable));
    }
}
