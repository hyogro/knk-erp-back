package knk.erp.api.shlee.account.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
    boolean existsByMemberIdAndDeletedFalse(String memberId);
    boolean existsByMemberId(String memberId);
    List<Member> findAllByDeletedIsFalse();
    Member findAllByMemberIdAndDeletedIsFalse(String memberId);
    Member findByMemberIdAndDeletedIsFalse(String memberId);
}
