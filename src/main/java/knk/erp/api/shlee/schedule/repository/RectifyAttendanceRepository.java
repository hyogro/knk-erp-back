package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface RectifyAttendanceRepository extends JpaRepository<RectifyAttendance, Long>, JpaSpecificationExecutor<RectifyAttendance> {
}
