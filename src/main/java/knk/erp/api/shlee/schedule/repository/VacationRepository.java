package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    List<Vacation> findAllByMemberIdAndDeletedIsFalse(String memberId);
    List<Vacation> findAllByDepartmentIdAndApproval1IsFalseAndDeletedIsFalse(Long departmentId);
    List<Vacation> findAllByApproval2IsFalseAndDeletedIsFalse();

    int countAllByDepartmentIdAndStartDateBeforeAndEndDateAfterAndDeletedIsFalse(Long departmentId, LocalDateTime today, LocalDateTime today2);
    int countAllByStartDateBeforeAndEndDateAfterAndDeletedIsFalse(LocalDateTime today, LocalDateTime today2);

}
