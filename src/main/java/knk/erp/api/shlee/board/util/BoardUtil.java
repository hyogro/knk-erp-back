package knk.erp.api.shlee.board.util;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.board.dto.board.BoardDTO;
import knk.erp.api.shlee.board.entity.Board;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardUtil {

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
        System.out.println(reader.getMemberId());
        for(String memberId : reference_memberId){
            if(memberId.equals(reader.getMemberId())) return true;
        }
        return false;
    }
}
