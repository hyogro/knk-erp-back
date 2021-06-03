package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
