package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
   int countByAttendanceDateAndMemberIdAndDeletedIsFalse(LocalDate today, String memberId);
   List<Attendance> findAllByMemberIdAndDeletedIsFalse(String memberId);
   int countByAttendanceDateAndDepartmentIdAndDeletedIsFalse(LocalDate today, Long departmentId);//부서별 오늘 출근한사람 목록
   int countByAttendanceDateAndDeletedIsFalse(LocalDate today);//오늘 출근한사람 목록
   int countByAttendanceDateAndDepartmentIdAndOnWorkAfterAndDeletedIsFalse(LocalDate today, Long departmentId, LocalDateTime nine);//부서별 오늘 지각한사람 목록
   int countByAttendanceDateAndOnWorkAfterAndDeletedIsFalse(LocalDate today, LocalDateTime nine);//오늘 지각한사람 목록
}
