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

        if(boardDTO.getReferenceMemberName() != null){
            if(boardDTO.getReferenceMemberName().size() > 0){
                target.setReferenceMemberName(boardDTO.getReferenceMemberName());
            }
        }

        if(boardDTO.getBoardType() != null){
            target.setBoardType(boardDTO.getBoardType());
        }

    }

    public boolean checkReference(List<String> reference_name, Member reader, MemberRepository memberRepository){
        System.out.println(reader.getMemberId());
        for(String name : reference_name){
            String reference_memberId = referenceNameToMemberId(name, memberRepository);
            if(reference_memberId.equals(reader.getMemberId())) return true;
        }
        return false;
    }

    public String referenceNameToMemberId(String name, MemberRepository memberRepository){
        Member m = memberRepository.findByMemberNameAndDeletedIsFalse(name);
        return m.getMemberId();
    }
}
