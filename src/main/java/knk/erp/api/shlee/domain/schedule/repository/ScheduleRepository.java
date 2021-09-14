package knk.erp.api.shlee.domain.schedule.repository;

import knk.erp.api.shlee.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {
}
