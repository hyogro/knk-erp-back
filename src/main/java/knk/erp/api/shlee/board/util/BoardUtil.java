package knk.erp.api.shlee.board.util;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.board.dto.board.BoardDTO;
import knk.erp.api.shlee.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardUtil {

    public Page<Board> findAllByReferenceMemberId(Authentication authentication, Page<Board> boardPage, Pageable pageable){
        List<Board> ref_BoardList = new ArrayList<>();
        for(int index = 0; index < boardPage.getContent().size(); index++){
            Board b = boardPage.getContent().get(index);

            int check = 0;
            if(!b.getReferenceMemberId().isEmpty()){
                for(String ref_id : b.getReferenceMemberId()){
                    if(ref_id.equals(authentication.getName())) check = 1;
                }
            }
            if(check == 1) ref_BoardList.add(b);
        }

        return new PageImpl<>(ref_BoardList, pageable, ref_BoardList.size());
    }

    public void updateSetBoard(Board target, BoardDTO boardDTO){
        if(boardDTO.getTitle() != null){
            target.setTitle(boardDTO.getTitle());
        }

        if(boardDTO.getContent() != null){
            target.setContent(boardDTO.getContent());
        }

        if(boardDTO.getReferenceMemberId() != null){
            if(boardDTO.getReferenceMemberId().size() > 0){
                target.setReferenceMemberId(boardDTO.getReferenceMemberId());
            }
        }

        if(boardDTO.getBoardType() != null){
            target.setBoardType(boardDTO.getBoardType());
        }

    }

    public boolean checkReference(List<String> reference_memberId, Member reader){
        for(String memberId : reference_memberId){
            if(memberId.equals(reader.getMemberId())) return true;
        }
        return false;
    }
}
