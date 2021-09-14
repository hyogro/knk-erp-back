package knk.erp.api.shlee.domain.schedule.repository;

import knk.erp.api.shlee.domain.schedule.entity.RectifyAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RectifyAttendanceRepository extends JpaRepository<RectifyAttendance, Long>, JpaSpecificationExecutor<RectifyAttendance> {
}
