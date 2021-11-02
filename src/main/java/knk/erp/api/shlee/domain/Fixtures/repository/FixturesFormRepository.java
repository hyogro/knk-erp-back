package knk.erp.api.shlee.domain.Fixtures.repository;

import knk.erp.api.shlee.domain.Fixtures.entity.FixturesForm;
import knk.erp.api.shlee.domain.account.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FixturesFormRepository extends JpaRepository<FixturesForm, Long> {
    List<FixturesForm> findAllByAuthorAndDeletedIsFalse(Member author);
    FixturesForm findByIdAndDeletedIsFalse(Long id);
    List<FixturesForm> findAllByDeletedIsFalse(Pageable pageable);
    int countAllByDeletedIsFalse();
    List<FixturesForm> findAllByCheckIsTrueAndDeletedIsFalse(Pageable pageable);
    int countAllByCheckIsFalseAndDeletedIsFalse();
    List<FixturesForm> findAllByCheckIsFalseAndDeletedIsFalse(Pageable pageable);
    int countAllByCheckIsTrueAndDeletedIsFalse();
    boolean existsByIdAndDeletedFalse(Long id);
}
