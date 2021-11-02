package knk.erp.api.shlee.domain.Evaluation.controller;


import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.Evaluation.dto.EvaluationDTO;
import knk.erp.api.shlee.domain.Evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation")
public class EvaluationController {
    private final EvaluationService evaluationService;

    @PostMapping("")
    public ResponseEntity<ResponseData> createEvaluation(@RequestBody EvaluationDTO evaluationDTO){
        evaluationService.createEvaluation(evaluationDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_EVALUATION_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ResponseData> readEvaluation(){
        String evaluation = evaluationService.readEvaluation();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_EVALUATION_SUCCESS)
                .data(evaluation)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }
}
