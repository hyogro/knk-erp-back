package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
