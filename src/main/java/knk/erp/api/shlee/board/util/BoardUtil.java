package knk.erp.api.shlee.board.util;

import knk.erp.api.shlee.board.dto.board.BoardDTO;
import knk.erp.api.shlee.board.dto.boardlist.BoardListDTO;
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

    public int getBoardSize(String boardType, BoardRepository boardRepository, String searchType, String keyword){
        List<Board> boardSize = new ArrayList<>();
        int totalPage;
        int elementsSize;

        if(boardType.equals("공지사항")) elementsSize = 15;
        else elementsSize = 10;

        switch (searchType) {
            case "제목검색":
                if(keyword.length()>=2) boardSize = boardRepository.findAllByTitleContainingAndBoardTypeAndDeletedFalse(keyword, boardType);
                break;

            case "작성자검색":
                if(keyword.length()>=2){
                    if(keyword.length()>=6) boardSize = boardRepository.findAllByWriterMemberIdContainingAndBoardTypeAndDeletedFalse(keyword, boardType);
                    else boardSize = boardRepository.findAllByWriterMemberNameContainingAndBoardTypeAndDeletedFalse(keyword, boardType);
                }
                break;

            case "참조":
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                boardSize = findAllByReferenceMemberId(authentication, boardRepository.findAllByBoardTypeAndDeletedFalse(boardType));
                break;

            default:
                boardSize = boardRepository.findAllByBoardTypeAndDeletedFalse(boardType);
                break;
        }

        totalPage = boardSize.size() / elementsSize;

        if(boardSize.size() % elementsSize != 0) totalPage++;

        return totalPage;
    }

    public Page<BoardListDTO> searchBoard(String searchType, String keyword, String boardType, BoardRepository boardRepository, Pageable pageable){
        int size;

        if(boardType.equals("공지사항")) size = 15;
        else size = 10;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, size, Sort.by("createDate").descending());
        List<Board> boardList = new ArrayList<>();
        switch (searchType) {
            case "제목검색":
                if(keyword.length()>=2) boardList = boardRepository.findAllByTitleContainingAndBoardTypeAndDeletedFalse(keyword, boardType, pageable);
                break;

            case "작성자검색":
                if(keyword.length()>=2){
                    if(keyword.length()>=6) boardList = boardRepository.findAllByWriterMemberIdContainingAndBoardTypeAndDeletedFalse(keyword, boardType, pageable);
                    else boardList = boardRepository.findAllByWriterMemberNameContainingAndBoardTypeAndDeletedFalse(keyword, boardType, pageable);
                }
                break;

            case "참조":
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                boardList = findAllByReferenceMemberId(authentication, boardRepository.findAllByBoardTypeAndDeletedFalse(boardType));
                break;

            default:
                boardList = boardRepository.findAllByBoardTypeAndDeletedFalse(boardType, pageable);
                break;
        }
        Page<Board> boardPage = new PageImpl<>(boardList, pageable, boardList.size());

        Page<BoardListDTO> page = boardPage.map(board -> new BoardListDTO(board.getIdx(), board.getTitle(),
                board.getWriterMemberName(), board.getCreateDate(), board.getBoardType(), board.getVisitors()));
        return page;
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

        List<File> file = new ArrayList<>();
        if(boardDTO.getFileName() != null){
            for(String f : boardDTO.getFileName()){
                file.add(fileRepository.findByFileName(f));
            }
            target.setFile(file);
        }
    }
}
