package knk.erp.api.shlee.board.service;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.board.dto.board.*;
import knk.erp.api.shlee.board.dto.boardlist.BoardListSearchDTO_REQ;
import knk.erp.api.shlee.board.dto.boardlist.Read_BoardListDTO_RES;
import knk.erp.api.shlee.board.entity.Board;
import knk.erp.api.shlee.board.entity.BoardRepository;
import knk.erp.api.shlee.board.util.BoardListUtil;
import knk.erp.api.shlee.board.util.BoardUtil;
import knk.erp.api.shlee.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardUtil boardUtil;
    private final CommonUtil commonUtil;
    private final BoardListUtil boardListUtil;

    // 게시글 생성
    @Transactional
    public Create_BoardDTO_RES createBoard(BoardDTO boardDTO){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member writer = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            if(boardDTO.getBoardType().equals("공지사항") && commonUtil.authorityToInteger(writer) <= 2){
                return new Create_BoardDTO_RES("CB003", "권한 부족");
            }
            boardDTO.setWriterMemberId(writer.getMemberId());
            boardDTO.setWriterDepId(writer.getDepartment().getId());
            Board board = boardDTO.toBoard();
            boardRepository.save(board);

            return new Create_BoardDTO_RES("CB001");
        }catch(Exception e){
            return new Create_BoardDTO_RES("CB002", e.getMessage());
        }
    }

    // 게시글 읽기
    @Transactional
    public Read_BoardDTO_RES readBoard(Long board_idx, BoardDTO boardDTO){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member reader = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            Board target = boardRepository.findByIdx(boardDTO.getIdx());
            Member writer = memberRepository.findAllByMemberIdAndDeletedIsFalse(target.getWriterMemberId());
            board_idx = target.getIdx();
            List<String> reference_name = target.getReference_memberName();
            if(reference_name.size() > 0 && !boardUtil.checkReference(reference_name, reader, memberRepository)){
                return new Read_BoardDTO_RES("RB003", "참조 대상이 아님");
            }
            else {
                return new Read_BoardDTO_RES("RB001", new Read_BoardDTO(target.getTitle(), target.getReference_memberName(),
                        target.getContent(), target.getBoardType().getValue(), writer.getMemberName(),
                        writer.getDepartment().getDepartmentName(), target.getCreateDate(), target.getUpdateDate()));
            }
        }catch(Exception e){
            return new Read_BoardDTO_RES("RB002", e.getMessage());
        }
    }

    // 게시글 수정
    @Transactional
    public Update_BoardDTO_RES updateBoard(Long board_idx, BoardDTO boardDTO){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member updater = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            Board target = boardRepository.findByIdx(boardDTO.getIdx());
            board_idx = target.getIdx();

            if(!target.getWriterMemberId().equals(updater.getMemberId())) {
                return new Update_BoardDTO_RES("UB003", "게시글 작성자가 아님");
            }

            if(boardDTO.getBoardType() != null){
                if(boardDTO.getBoardType().equals("공지사항") && commonUtil.authorityToInteger(updater) <= 2){
                    return new Update_BoardDTO_RES("UB004", "권한 부족");
                }
            }

            boardUtil.updateSetBoard(target, boardDTO);

            boardRepository.save(target);

            return new Update_BoardDTO_RES("UB001");
        }catch(Exception e){
            return new Update_BoardDTO_RES("UB002", e.getMessage());
        }
    }

    // 게시글 삭제
    @Transactional
    public Delete_BoardDTO_RES deleteBoard(Long board_idx, BoardDTO boardDTO){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member deleter = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            Board target = boardRepository.findByIdx(boardDTO.getIdx());
            board_idx = target.getIdx();

            if(!target.getWriterMemberId().equals(deleter.getMemberId())) {
                return new Delete_BoardDTO_RES("DB003", "게시글 작성자가 아님");
            }
            else {
                target.setDeleted(true);
                boardRepository.save(target);

                return new Delete_BoardDTO_RES("DB001");
            }
        }catch(Exception e){
            return new Delete_BoardDTO_RES("DB002", e.getMessage());
        }
    }

    // 게시글 목록 기본 보기
    @Transactional
    public Read_BoardListDTO_RES readList(Pageable pageable, BoardListSearchDTO_REQ boardListSearchDTOReq){
        try{
            return new Read_BoardListDTO_RES("RBL001", boardListUtil.findAllBoardList(pageable, boardRepository));
        }catch(Exception e){
            return new Read_BoardListDTO_RES("RBL002", e.getMessage());
        }
    }
}

