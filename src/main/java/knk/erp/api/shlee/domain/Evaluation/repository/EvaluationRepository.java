package knk.erp.api.shlee.domain.Evaluation.repository;

import knk.erp.api.shlee.domain.Evaluation.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Evaluation findAllByDeletedIsFalse();
}
