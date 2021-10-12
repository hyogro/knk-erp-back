package knk.erp.api.shlee.exception.exceptions.common;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class NotExistsInputDataException extends CustomException {
    public NotExistsInputDataException() { super(ExceptionCode.INPUT_DATA_ERROR); }
}
