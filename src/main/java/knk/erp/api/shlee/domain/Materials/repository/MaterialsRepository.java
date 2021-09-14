package knk.erp.api.shlee.domain.Materials.repository;

import knk.erp.api.shlee.domain.Materials.entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialsRepository extends JpaRepository<Materials, Long> {
    Materials findAllByDeletedFalse();
}
