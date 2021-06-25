package knk.erp.api.shlee.board.service;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.board.dto.board.*;
import knk.erp.api.shlee.board.dto.boardlist.BoardListDTO;
import knk.erp.api.shlee.board.dto.boardlist.NoticeLatestDTO_RES;
import knk.erp.api.shlee.board.dto.boardlist.Read_NoticeBoardDTO_RES;
import knk.erp.api.shlee.board.dto.boardlist.Read_WorkBoardListDTO_RES;
import knk.erp.api.shlee.board.entity.Board;
import knk.erp.api.shlee.board.entity.BoardRepository;
import knk.erp.api.shlee.board.util.BoardUtil;
import knk.erp.api.shlee.common.util.CommonUtil;
import knk.erp.api.shlee.file.entity.File;
import knk.erp.api.shlee.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardUtil boardUtil;
    private final CommonUtil commonUtil;
    private final FileRepository fileRepository;

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
            boardDTO.setWriterMemberName(writer.getMemberName());
            boardDTO.setWriterDepId(writer.getDepartment().getId());
            Board board = boardDTO.toBoard();
            List<File> file = new ArrayList<>();
            if(boardDTO.getFileName() != null){
                for(String f : boardDTO.getFileName()){
                    file.add(fileRepository.findByFileName(f));
                }
                board.setFile(file);
            }
            boardRepository.save(board);

            return new Create_BoardDTO_RES("CB001");
        }catch(Exception e){
            return new Create_BoardDTO_RES("CB002", e.getMessage());
        }
    }

    // 게시글 읽기
    @Transactional
    public Read_BoardDTO_RES readBoard(Long board_idx){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member reader = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            Board target = boardRepository.findByIdxAndDeletedFalse(board_idx);
            Member writer = memberRepository.findAllByMemberIdAndDeletedIsFalse(target.getWriterMemberId());

            List<String> visitors;

            if(target.getVisitors() != null){
                visitors = new ArrayList<>(target.getVisitors());
                if(!visitors.contains(reader.getMemberId())){
                    visitors.add(reader.getMemberId());
                }
            }
            else {
                visitors = new ArrayList<>();
                visitors.add(reader.getMemberId());
            }

            target.setVisitors(visitors);
            int count = target.getCount() + 1;
            target.setCount(count);
            boardRepository.save(target);

            return new Read_BoardDTO_RES("RB001", new Read_BoardDTO(target.getTitle(), target.getReferenceMemberId(),
                    target.getContent(), target.getBoardType(), writer.getMemberName(), writer.getMemberId(),
                    writer.getDepartment().getDepartmentName(), target.getCreateDate(), target.getUpdateDate(), target.getFile(),
                    target.getCount(), target.getVisitors()));
        }catch(Exception e){
            e.printStackTrace();
            return new Read_BoardDTO_RES("RB002", e.getMessage());
        }
    }

    // 게시글 수정
    @Transactional
    public Update_BoardDTO_RES updateBoard(Long board_idx, BoardDTO boardDTO){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member updater = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            Board target = boardRepository.findByIdxAndDeletedFalse(board_idx);

            if(!target.getWriterMemberId().equals(updater.getMemberId())) {
                return new Update_BoardDTO_RES("UB003", "게시글 작성자가 아님");
            }

            boardUtil.updateSetBoard(target, boardDTO, fileRepository);

            boardRepository.save(target);

            return new Update_BoardDTO_RES("UB001");
        }catch(Exception e){
            return new Update_BoardDTO_RES("UB002", e.getMessage());
        }
    }

    // 게시글 삭제
    @Transactional
    public Delete_BoardDTO_RES deleteBoard(Long board_idx){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member deleter = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
            Board target = boardRepository.findByIdxAndDeletedFalse(board_idx);

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

    // 업무게시판 목록 보기
    @Transactional
    public Read_WorkBoardListDTO_RES workBoardList(Pageable pageable, String searchType, String keyword){
        try{
            Page<BoardListDTO> page = boardUtil.searchBoard(searchType, keyword, "업무게시판", boardRepository, pageable);
            int totalPage = boardUtil.getBoardSize("업무게시판", boardRepository, searchType, keyword);

            return new Read_WorkBoardListDTO_RES("RWB001", page, totalPage);
        }catch(Exception e){
            return new Read_WorkBoardListDTO_RES("RWB002", e.getMessage());
        }
    }

    // 공지사항 목록 보기
    @Transactional
    public Read_NoticeBoardDTO_RES noticeBoardList(Pageable pageable, String searchType, String keyword){
        try{
            Page<BoardListDTO> page = boardUtil.searchBoard(searchType, keyword, "공지사항", boardRepository, pageable);
            int totalPage = boardUtil.getBoardSize("업무게시판", boardRepository, searchType, keyword);

            return new Read_NoticeBoardDTO_RES("RNB001", page, totalPage);
        }catch(Exception e){
            return new Read_NoticeBoardDTO_RES("RNB002", e.getMessage());
        }
    }

    // 공지사항 최신순 5개 보기
    @Transactional
    public NoticeLatestDTO_RES noticeLatest(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, 5, Sort.by("createDate").descending());
        List<Board> allList = boardRepository.findAllByBoardTypeAndDeletedFalse("공지사항", pageable);
        Page<Board> all = new PageImpl<>(allList, pageable, allList.size());
        List<Board> latest = new ArrayList<>();
        try{
            for(int i = 0; i < 5; i++){
                if(i < all.getContent().size()) latest.add(all.getContent().get(i));
            }
            Page<Board> boardPage = new PageImpl<>(latest, pageable, latest.size());
            Page<BoardListDTO> page = boardPage.map(board -> new BoardListDTO(board.getIdx(), board.getTitle(),
                    board.getWriterMemberName(), board.getCreateDate(), board.getBoardType(), board.getVisitors()));
            return new NoticeLatestDTO_RES("NBL001", page);
        }catch(Exception e){
            return new NoticeLatestDTO_RES("NBL002", e.getMessage());
        }
    }

    // 멤버 id와 이름 목록 불러오기
    @Transactional
    public MemberIdListDTO_RES memberIdList(){
        try{
            List<Member> all = memberRepository.findAllByDeletedIsFalse();
            List<Get_memberIdListDTO> memberList = new ArrayList<>();
            for(Member m : all){
                memberList.add(new Get_memberIdListDTO(m.getMemberId(), m.getMemberName()));
            }

            return new MemberIdListDTO_RES("MIL001", memberList);
        }catch(Exception e){
            return new MemberIdListDTO_RES("MIL002", e.getMessage());
        }
    }
}