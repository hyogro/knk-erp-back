package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {
   Optional<Attendance> findByAttendanceDateAndMemberIdAndDeletedIsFalse(LocalDate today, String memberId);
   Attendance findByIdAndDeletedIsFalse(Long id);

   int countByAttendanceDateAndMemberIdAndDeletedIsFalse(LocalDate today, String memberId);
   List<Attendance> findAllByMemberIdAndDeletedIsFalse(String memberId);
   int countByAttendanceDateAndDepartmentIdAndDeletedIsFalse(LocalDate today, Long departmentId);//부서별 오늘 출근한사람 목록
   int countByAttendanceDateAndDeletedIsFalse(LocalDate today);//오늘 출근한사람 목록
   int countByAttendanceDateAndDepartmentIdAndOnWorkAfterAndDeletedIsFalse(LocalDate today, Long departmentId, LocalTime nine);//부서별 오늘 지각한사람 목록
   int countByAttendanceDateAndOnWorkAfterAndDeletedIsFalse(LocalDate today, LocalTime nine);//오늘 지각한사람 목록
}
