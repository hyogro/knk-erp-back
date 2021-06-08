package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByDeletedIsFalse();
    Page<Schedule> findAllByViewOptionAndDeletedIsFalse(String viewOption, Pageable pageable);
    Page<Schedule> findAllByViewOptionAndDepartmentIdAndDeletedIsFalse(String viewOption, Long departmentId, Pageable pageable);
    Page<Schedule> findAllByViewOptionAndMemberIdAndDeletedIsFalse(String viewOption, String memberId, Pageable pageable);
    Page<Schedule> findAllByDeletedIsFalseAndMemberIdAndEndDateAfter(String memberId, LocalDateTime today, Pageable pageable);
}
