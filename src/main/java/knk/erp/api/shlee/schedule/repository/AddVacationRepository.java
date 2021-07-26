package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.AddVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddVacationRepository extends JpaRepository<AddVacation, Long>, JpaSpecificationExecutor<AddVacation> {
}
