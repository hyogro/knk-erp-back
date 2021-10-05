package knk.erp.api.shlee.domain.Evaluation.controller;


import knk.erp.api.shlee.domain.Evaluation.dto.Create_EvaluationDTO_RES;
import knk.erp.api.shlee.domain.Evaluation.dto.EvaluationDTO;
import knk.erp.api.shlee.domain.Evaluation.dto.Read_EvaluationDTO_RES;
import knk.erp.api.shlee.domain.Evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation")
public class EvaluationController {
    private final EvaluationService evaluationService;

    @PostMapping("")
    public ResponseEntity<Create_EvaluationDTO_RES> createEvaluation(@RequestBody EvaluationDTO evaluationDTO){
        return ResponseEntity.ok(evaluationService.createEvaluation(evaluationDTO));
    }

    @GetMapping("")
    public ResponseEntity<Read_EvaluationDTO_RES> readEvaluation(){
        return ResponseEntity.ok(evaluationService.readEvaluation());
    }
}
