package knk.erp.api.shlee.domain.schedule.repository;

import knk.erp.api.shlee.domain.schedule.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VacationRepository extends JpaRepository<Vacation, Long>, JpaSpecificationExecutor<Vacation> {
}
