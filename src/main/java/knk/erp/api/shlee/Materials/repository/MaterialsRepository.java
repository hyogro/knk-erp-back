package knk.erp.api.shlee.Materials.repository;

import knk.erp.api.shlee.Materials.entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialsRepository extends JpaRepository<Materials, Long> {
    Materials findAllByDeletedFalse();
}
