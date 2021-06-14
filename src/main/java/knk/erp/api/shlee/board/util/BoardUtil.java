package knk.erp.api.shlee.board.util;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.board.dto.board.BoardDTO;
import knk.erp.api.shlee.board.entity.Board;
import knk.erp.api.shlee.file.entity.File;
import knk.erp.api.shlee.file.repository.FileRepository;
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
        for(Board b : boardPage.getContent()){
            if(b.getReferenceMemberId().contains(authentication.getName()))ref_BoardList.add(b);
        }

        return new PageImpl<>(ref_BoardList, pageable, ref_BoardList.size());
    }

    public void updateSetBoard(Board target, BoardDTO boardDTO, FileRepository fileRepository){
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

        List<File> file = new ArrayList<>();
        if(boardDTO.getFileName() != null){
            for(String f : boardDTO.getFileName()){
                file.add(fileRepository.findByFileName(f));
            }
            target.setFile(file);
        }

    }

    public boolean checkReference(List<String> reference_memberId, Member reader){
        for(String memberId : reference_memberId){
            if(memberId.equals(reader.getMemberId())) return true;
            else if(memberId.equals("null")) return true;
        }
        return false;
    }
}
