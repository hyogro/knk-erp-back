package knk.erp.api.shlee.exception.exceptions.common;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class ErrorInputDataException extends CustomException {
    public ErrorInputDataException() { super(ExceptionCode.INPUT_DATA_ERROR); }
}
