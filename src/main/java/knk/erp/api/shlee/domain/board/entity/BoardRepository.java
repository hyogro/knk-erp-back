package knk.erp.api.shlee.domain.board.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByIdxAndDeletedFalse(Long idx);
    List<Board> findAllByBoardTypeAndDeletedFalse(String boardType);
    List<Board> findAllByBoardTypeAndDeletedFalse(String boardType, Pageable pageable);
    List<Board> findAllByTitleContainingAndBoardTypeAndDeletedFalse(String title, String boardType);
    List<Board> findAllByTitleContainingAndBoardTypeAndDeletedFalse(String title, String boardType, Pageable pageable);
    List<Board> findAllByWriterMemberIdContainingAndBoardTypeAndDeletedFalse(String memberId, String boardType);
    List<Board> findAllByWriterMemberIdContainingAndBoardTypeAndDeletedFalse(String memberId, String boardType, Pageable pageable);
    List<Board> findAllByWriterMemberNameContainingAndBoardTypeAndDeletedFalse(String memberName, String boardType);
    List<Board> findAllByWriterMemberNameContainingAndBoardTypeAndDeletedFalse(String memberName, String boardType, Pageable pageable);

    boolean existsByIdxAndDeletedIsFalse(Long idx);
}
