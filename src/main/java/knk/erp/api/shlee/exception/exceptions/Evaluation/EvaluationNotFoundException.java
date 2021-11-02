package knk.erp.api.shlee.exception.exceptions.Evaluation;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class EvaluationNotFoundException extends CustomException {
    public EvaluationNotFoundException() { super(ExceptionCode.NOT_FOUND_EVALUATION); }
}
