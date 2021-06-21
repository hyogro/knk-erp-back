package knk.erp.api.shlee.board.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByIdxAndDeletedFalse(Long idx);
    List<Board> findAllByBoardTypeAndDeletedFalse(String boardType);
    List<Board> findAllByBoardTypeAndDeletedFalse(String boardType, Pageable pageable);
    List<Board> findAllByTitleContainingAndBoardTypeAndDeletedFalse(String title, String boardType, Pageable pageable);
    List<Board> findAllByWriterMemberIdAndBoardTypeAndDeletedFalse(String memberId, String boardType, Pageable pageable);
    List<Board> findAllByWriterMemberNameAndBoardTypeAndDeletedFalse(String memberName, String boardType, Pageable pageable);
}
