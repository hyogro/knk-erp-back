package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RectifyAttendanceRepository extends JpaRepository<RectifyAttendance, Long> {
    List<RectifyAttendance> findAllByMemberIdAndDeletedIsFalse(String memberId);
    List<RectifyAttendance> findAllByDepartmentIdAndDeletedIsFalse(Long departmentId);
    List<RectifyAttendance> findAllByDeletedIsFalse();
}
