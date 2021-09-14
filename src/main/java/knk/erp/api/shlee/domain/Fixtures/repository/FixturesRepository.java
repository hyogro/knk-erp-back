package knk.erp.api.shlee.domain.Fixtures.repository;

import knk.erp.api.shlee.domain.Fixtures.entity.Fixtures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixturesRepository extends JpaRepository<Fixtures, Long> {
    Fixtures findByIdAndDeletedIsFalse(Long id);
}
