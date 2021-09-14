package knk.erp.api.shlee.domain.account.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByLeader_MemberIdAndDeletedFalse(String leaderId);
    Department findByDepartmentNameAndDeletedFalse(String departmentName);
    List<Department> findAllByDeletedFalse();
    boolean existsByIdAndDeletedFalse(Long id);
    boolean existsByDepartmentNameAndDeletedFalse(String departmentName);
    Department findByIdAndDeletedFalse(Long id);
}
