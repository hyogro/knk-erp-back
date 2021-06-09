package knk.erp.api.shlee.board.service;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.board.dto.BoardDTO_REQ;
import knk.erp.api.shlee.board.dto.Create_BoardDTO_RES;
import knk.erp.api.shlee.board.entity.Board;
import knk.erp.api.shlee.board.entity.BoardRepository;
import knk.erp.api.shlee.board.util.BoardUtil;
import knk.erp.api.shlee.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardUtil boardUtil;
    private final CommonUtil commonUtil;

    @Transactional
    public Create_BoardDTO_RES createBoard(BoardDTO_REQ boardDTOReq){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member writer_memberId = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());

            if(boardDTOReq.getBoardType().equals("공지사항") && commonUtil.authorityToInteger(writer_memberId) <= 2){
                return new Create_BoardDTO_RES("CB003", "권한 부족");
            }

            Board board = boardDTOReq.toBoard();
            board.setMemberId(writer_memberId.getMemberId());
            board.setDep_id(writer_memberId.getDepartment().getId());
            boardRepository.save(board);

            return new Create_BoardDTO_RES("CB001");
        }catch(Exception e){
            return new Create_BoardDTO_RES("CB002", e.getMessage());
        }
    }
}

