package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
   int countByAttendanceDateAndMemberIdAndDeletedIsFalse(LocalDate today, String memberId);
   List<Attendance> findAllByMemberIdAndDeletedIsFalse(String memberId);
}
