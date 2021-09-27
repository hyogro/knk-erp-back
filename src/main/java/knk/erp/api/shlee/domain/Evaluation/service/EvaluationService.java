package knk.erp.api.shlee.domain.Evaluation.service;


import knk.erp.api.shlee.domain.Evaluation.dto.Create_EvaluationDTO_RES;
import knk.erp.api.shlee.domain.Evaluation.dto.EvaluationDTO;
import knk.erp.api.shlee.domain.Evaluation.dto.Read_EvaluationDTO_RES;
import knk.erp.api.shlee.domain.Evaluation.entity.Evaluation;
import knk.erp.api.shlee.domain.Evaluation.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    //평가표 파일 이름 저장
    public Create_EvaluationDTO_RES createEvaluation(EvaluationDTO evaluationDTO) {
        try{
            Evaluation before = evaluationRepository.findAllByDeletedIsFalse();

            if(before != null) {
                before.setDeleted(true);
                evaluationRepository.save(before);
            }

            evaluationRepository.save(Evaluation.builder().evaluation(evaluationDTO.getEvaluation()).build());

            return new Create_EvaluationDTO_RES("CEV001");
        }catch(Exception e){
            return new Create_EvaluationDTO_RES("CEV002", e.getMessage());
        }
    }

    //평가표 파일 이름 Get
    public Read_EvaluationDTO_RES readEvaluation(){
        try{
            Evaluation evaluation = evaluationRepository.findAllByDeletedIsFalse();

            return new Read_EvaluationDTO_RES("REV001", "평가표 읽기 성공", evaluation.getEvaluation());
        }catch(Exception e){
            return new Read_EvaluationDTO_RES("REV002", e.getMessage());
        }
    }
}
