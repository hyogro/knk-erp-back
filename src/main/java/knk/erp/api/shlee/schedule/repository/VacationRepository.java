package knk.erp.api.shlee.schedule.repository;

import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    List<Vacation> findAllByMemberIdAndDeletedIsFalse(String memberId);
    List<Vacation> findAllByDepartmentIdAndApproval1IsFalseAndDeletedIsFalse(Long departmentId);
    List<Vacation> findAllByApproval2IsFalseAndDeletedIsFalse();

    int countAllByDepartmentIdAndStartDate_DateBeforeAndEndDate_DateAfterAndDeletedIsFalse(Long departmentId, LocalDate today, LocalDate today2);
    int countAllByStartDate_DateBeforeAndEndDate_DateAfterAndDeletedIsFalse(LocalDate today, LocalDate today2);

}
