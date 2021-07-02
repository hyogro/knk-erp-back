package knk.erp.api.shlee.Fixtures.repository;

import knk.erp.api.shlee.Fixtures.entity.Fixtures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixturesRepository extends JpaRepository<Fixtures, Long> {
    Fixtures findByIdAndDeletedIsFalse(Long id);
}
