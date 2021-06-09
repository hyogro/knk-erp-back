package knk.erp.api.shlee.board.util;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.board.dto.BoardDTO_REQ;
import knk.erp.api.shlee.board.entity.Board;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardUtil {

    public void updateSetBoard(Board target, BoardDTO_REQ boardDTOReq){
        if(boardDTOReq.getTitle() != null){
            target.setTitle(boardDTOReq.getTitle());
        }

        if(boardDTOReq.getContent() != null){
            target.setContent(boardDTOReq.getContent());
        }

        if(boardDTOReq.getReference_memberName().size() > 0){
            target.setReference_memberName(boardDTOReq.getReference_memberName());
        }
    }

    public boolean checkReference(List<String> reference_name, Member reader, MemberRepository memberRepository){
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
