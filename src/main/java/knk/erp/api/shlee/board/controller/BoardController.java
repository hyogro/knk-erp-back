package knk.erp.api.shlee.board.controller;

import knk.erp.api.shlee.board.dto.*;
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
    public ResponseEntity<Create_BoardDTO_RES> createBoard(@RequestBody BoardDTO_REQ boardDTOReq){
        return ResponseEntity.ok(boardService.createBoard(boardDTOReq));
    }

    // 게시글 읽기
    @PostMapping("/readBoard/{board_idx}")
    public ResponseEntity<Read_BoardDTO_RES> readBoard(@PathVariable Long board_idx, @RequestBody BoardDTO_REQ boardDTOReq){
        return ResponseEntity.ok(boardService.readBoard(board_idx, boardDTOReq));
    }

    // 게시글 수정
    @PostMapping("/updateBoard")
    public ResponseEntity<Update_BoardDTO_RES> updateBoard(@RequestBody BoardDTO_REQ boardDTOReq){
        return ResponseEntity.ok(boardService.updateBoard(boardDTOReq));
    }

    // 게시글 삭제
    @PostMapping("/deleteBoard")
    public ResponseEntity<Delete_BoardDTO_RES> deleteBoard(@RequestBody BoardDTO_REQ boardDTOReq){
        return ResponseEntity.ok(boardService.deleteBoard(boardDTOReq));
    }
}
