package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
}
