package knk.erp.api.shlee.domain.board.service;

import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import knk.erp.api.shlee.domain.board.dto.board.*;
import knk.erp.api.shlee.domain.board.dto.boardlist.*;
import knk.erp.api.shlee.domain.board.entity.Board;
import knk.erp.api.shlee.domain.board.entity.BoardRepository;
import knk.erp.api.shlee.domain.board.util.BoardUtil;
import knk.erp.api.shlee.common.util.AuthorityUtil;
import knk.erp.api.shlee.domain.file.entity.File;
import knk.erp.api.shlee.domain.file.repository.FileRepository;
import knk.erp.api.shlee.exception.exceptions.Board.BoardNotAuthorException;
import knk.erp.api.shlee.exception.exceptions.Board.BoardNotFoundException;
import knk.erp.api.shlee.exception.exceptions.common.PermissionDeniedException;
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
    private final AuthorityUtil authorityUtil;
    private final FileRepository fileRepository;

    /* 게시글 생성 */
    @Transactional
    public void createBoard(BoardDTO boardDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member writer = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());

        if(boardDTO.getBoardType().equals("공지사항") || boardDTO.getBoardType().equals("현장팀게시판")) { throwIfPermissionDenied(writer); }

        boardDTO.setWriterMemberId(writer.getMemberId());
        boardDTO.setWriterMemberName(writer.getMemberName());
        boardDTO.setWriterDepId(writer.getDepartment().getId());
        Board board = boardDTO.toBoard();

        if(boardDTO.getFileName() != null){
            List<File> file = new ArrayList<>();
            for(String f : boardDTO.getFileName()){
                file.add(fileRepository.findByFileName(f));
            }
            board.setFile(file);
        }
        boardRepository.save(board);
    }

    // 공지사항 생성 권한부족 예외처리
    public void throwIfPermissionDenied(Member writer){
        if(authorityUtil.authorityToInteger(writer) <= 2 && !writer.getAuthority().equals(Authority.ROLE_MANAGE)){
            throw new PermissionDeniedException();
        }
    }

    /* 게시글 읽기 */
    @Transactional
    public Read_BoardDTO_RES readBoard(Long board_idx){
        throwIfNotFoundBoard(board_idx);
        Board target = boardRepository.findByIdxAndDeletedFalse(board_idx);
        Member writer = memberRepository.findAllByMemberIdAndDeletedIsFalse(target.getWriterMemberId());

        List<String> visitors = boardGetVisitors(target);
        List<Read_ReferenceMemberDTO> reference = boardGetReferenced(target);
        target.setVisitors(visitors);
        int count = target.getCount() + 1;
        target.setCount(count);
        boardRepository.save(target);

        return new Read_BoardDTO_RES(new Read_BoardDTO(target.getTitle(), target.getContent(), target.getBoardType(),
                writer.getMemberName(), writer.getMemberId(), writer.getDepartment().getDepartmentName(), target.getCreateDate(),
                target.getUpdateDate(), target.getFile(), target.getCount(), target.getVisitors()), reference);
    }

    //참조대상 리스트 반환
    public List<Read_ReferenceMemberDTO> boardGetReferenced(Board target){
        List<Read_ReferenceMemberDTO> reference = new ArrayList<>();
        if(target.getReferenceMemberId() != null){
            List<String> referenceMemberId = target.getReferenceMemberId();

            for(String s : referenceMemberId){
                Member member = memberRepository.findByMemberIdAndDeletedIsFalse(s);
                String name = member.getMemberName();
                reference.add(new Read_ReferenceMemberDTO(s, name));
            }
        }
        return reference;
    }

    //방문자 리스트 반환
    public List<String> boardGetVisitors(Board target){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member reader = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());

        List<String> visitors;

        if(target.getVisitors() != null ){
            visitors = new ArrayList<>(target.getVisitors());
            if(!visitors.contains(reader.getMemberId())){
                visitors.add(reader.getMemberId());
            }
        }
        else {
            visitors = new ArrayList<>();
            visitors.add(reader.getMemberId());
        }

        return visitors;
    }

    //삭제되었거나 존재하지않는 게시글 접근 예외처리
    public void throwIfNotFoundBoard(Long idx){
        if(!boardRepository.existsByIdxAndDeletedIsFalse(idx)) {
            throw new BoardNotFoundException();
        }
    }

    /* 게시글 수정 */
    @Transactional
    public void updateBoard(Long board_idx, BoardDTO boardDTO){
        throwIfNotFoundBoard(board_idx);
        Board target = boardRepository.findByIdxAndDeletedFalse(board_idx);

        throwIfBoardNotAuthor(target);

        boardUtil.updateSetBoard(target, boardDTO, fileRepository);

        boardRepository.save(target);
    }

    //게시글 작성자가 아님
    public void throwIfBoardNotAuthor(Board target){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member updater = memberRepository.findAllByMemberIdAndDeletedIsFalse(authentication.getName());
        if(!target.getWriterMemberId().equals(updater.getMemberId())) {
            throw new BoardNotAuthorException();
        }
    }

    /* 게시글 삭제 */
    @Transactional
    public void deleteBoard(Long board_idx){
        throwIfNotFoundBoard(board_idx);
        Board target = boardRepository.findByIdxAndDeletedFalse(board_idx);

        throwIfBoardNotAuthor(target);

        target.setDeleted(true);
        boardRepository.save(target);
    }

    /* 업무게시판 목록 보기 */
    @Transactional
    public Read_BoardListDTO_RES workBoardList(Pageable pageable, String searchType, String keyword){
        Page<BoardListDTO> page = boardUtil.searchBoard(searchType, keyword, "업무게시판", boardRepository, pageable);
        int totalPage = boardUtil.getBoardSize("업무게시판", boardRepository, searchType, keyword);

        return new Read_BoardListDTO_RES(page, totalPage);
    }

    /* 현장팀 게시판 목록 보기 */
    @Transactional
    public Read_BoardListDTO_RES fieldTeamBoardList(Pageable pageable, String searchType, String keyword){
        Page<BoardListDTO> page = boardUtil.searchBoard(searchType, keyword, "현장팀게시판", boardRepository, pageable);
        int totalPage = boardUtil.getBoardSize("현장팀게시판", boardRepository, searchType, keyword);

        return new Read_BoardListDTO_RES(page, totalPage);
    }

    /* 공지사항 목록 보기 */
    @Transactional
    public Read_BoardListDTO_RES noticeBoardList(Pageable pageable, String searchType, String keyword){
        Page<BoardListDTO> page = boardUtil.searchBoard(searchType, keyword, "공지사항", boardRepository, pageable);
        int totalPage = boardUtil.getBoardSize("공지사항", boardRepository, searchType, keyword);

        return new Read_BoardListDTO_RES(page, totalPage);
    }

    /* 공지사항 최신순 5개 보기 */
    @Transactional
    public Page<BoardListDTO> noticeLatest(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, 5, Sort.by("createDate").descending());
        List<Board> allList = boardRepository.findAllByBoardTypeAndDeletedFalse("공지사항", pageable);
        Page<Board> all = new PageImpl<>(allList, pageable, allList.size());
        List<Board> latest = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            if(i < all.getContent().size()) latest.add(all.getContent().get(i));
        }
        return boardUtil.getBoardListDTOS(pageable, latest);
    }

    /* 멤버 id와 이름 목록 불러오기 */
    @Transactional
    public List<Get_memberIdListDTO> memberIdList(){
        List<Member> all = memberRepository.findAllByDeletedIsFalse();
        List<Get_memberIdListDTO> memberList = new ArrayList<>();
        for(Member m : all){
            memberList.add(new Get_memberIdListDTO(m.getMemberId(), m.getMemberName()));
        }

        return memberList;
    }
}