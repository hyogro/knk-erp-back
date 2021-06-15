package knk.erp.api.shlee.account.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByLeader_MemberIdAndDeletedFalse(String leaderId);
    Department findByDepartmentNameAndDeletedFalse(String departmentName);
    List<Department> findAllByDeletedIsFalse();
    boolean existsByDepartmentNameAndDeletedIsFalse(String departmentName);
    Department findByIdAndDeletedFalse(Long id);
}
