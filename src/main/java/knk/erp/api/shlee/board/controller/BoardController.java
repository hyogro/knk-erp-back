package knk.erp.api.shlee.board.controller;

import knk.erp.api.shlee.board.dto.board.*;
import knk.erp.api.shlee.board.service.BoardService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(boardService.readBoard(board_idx, boardDTO));
    }

    // 게시글 수정
    @PostMapping("/{board_idx}/updateBoard")
    public ResponseEntity<Update_BoardDTO_RES> updateBoard(@PathVariable Long board_idx, @RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.updateBoard(board_idx, boardDTO));
    }

    // 게시글 삭제
    @PostMapping("/{board_idx}/deleteBoard")
    public ResponseEntity<Delete_BoardDTO_RES> deleteBoard(@PathVariable Long board_idx, @RequestBody BoardDTO boardDTOReq){
        return ResponseEntity.ok(boardService.deleteBoard(board_idx, boardDTOReq));
    }

}
