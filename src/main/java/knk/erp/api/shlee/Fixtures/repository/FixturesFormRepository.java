package knk.erp.api.shlee.Fixtures.repository;

import knk.erp.api.shlee.Fixtures.entity.FixturesForm;
import knk.erp.api.shlee.account.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FixturesFormRepository extends JpaRepository<FixturesForm, Long> {
    List<FixturesForm> findAllByAuthorAndDeletedIsFalse(Member author);
}
