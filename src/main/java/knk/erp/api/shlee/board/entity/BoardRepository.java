package knk.erp.api.shlee.board.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByIdx(Long idx);
    Page<Board> findAllByDeletedIsFalse(Pageable pageable);
    Page<Board> findAllByTitleContainingAndDeletedFalse(Pageable pageable, String title);
    Page<Board> findAllByBoardTypeContainingAndDeletedFalse(String boardType, Pageable pageable);
    Page<Board> findAllByWriterMemberIdAndDeletedFalse(Pageable pageable, String memberId);
}