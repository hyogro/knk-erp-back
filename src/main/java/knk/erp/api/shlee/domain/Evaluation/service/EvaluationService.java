package knk.erp.api.shlee.domain.Evaluation.service;


import knk.erp.api.shlee.domain.Evaluation.dto.EvaluationDTO;
import knk.erp.api.shlee.domain.Evaluation.entity.Evaluation;
import knk.erp.api.shlee.domain.Evaluation.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    /* 평가표 파일 이름 저장 */
    public void createEvaluation(EvaluationDTO evaluationDTO) {
        Evaluation before = evaluationRepository.findAllByDeletedIsFalse();

        if(before != null) {
            before.setDeleted(true);
            evaluationRepository.save(before);
        }

        evaluationRepository.save(Evaluation.builder().evaluation(evaluationDTO.getEvaluation()).build());
    }

    /* 평가표 파일 이름 Get */
    public String readEvaluation() { return evaluationRepository.findAllByDeletedIsFalse().getEvaluation(); }
}
