package knk.erp.api.shlee.board.util;

import knk.erp.api.shlee.board.dto.board.BoardDTO;
import knk.erp.api.shlee.board.entity.Board;
import knk.erp.api.shlee.board.entity.BoardRepository;
import knk.erp.api.shlee.file.entity.File;
import knk.erp.api.shlee.file.repository.FileRepository;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardUtil {

    public Page<Board> searchBoard(String searchType, String keyword, String boardType, BoardRepository boardRepository, Pageable pageable){
        List<Board> boardList = new ArrayList<>();
        switch (searchType) {
            case "제목검색":
                if(keyword.length()>=2) boardList = boardRepository.findAllByTitleContainingAndBoardTypeAndDeletedFalse(keyword, boardType, pageable);
                break;

            case "작성자검색":
                if(keyword.length()>=2){
                    if(keyword.length()>=6) boardList = boardRepository.findAllByWriterMemberIdAndBoardTypeAndDeletedFalse(keyword, boardType, pageable);
                    else boardList = boardRepository.findAllByWriterMemberNameAndBoardTypeAndDeletedFalse(keyword, boardType, pageable);
                }
                break;

            case "참조":
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                boardList = findAllByReferenceMemberId(authentication, boardRepository.findAllByBoardTypeAndDeletedFalse(boardType, pageable));
                break;

            default:
                boardList = boardRepository.findAllByBoardTypeAndDeletedFalse(boardType, pageable);
                break;
        }
        return new PageImpl<>(boardList, pageable, boardList.size());
    }

    public List<Board> findAllByReferenceMemberId(Authentication authentication, List<Board> boardList){
        List<Board> ref_BoardList = new ArrayList<>();
        for(Board b : boardList){
            if(b.getReferenceMemberId() != null &&b.getReferenceMemberId().contains(authentication.getName())) ref_BoardList.add(b);
        }

        return ref_BoardList;
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
}
