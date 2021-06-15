package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long>, JpaSpecificationExecutor<Vacation> {

}
