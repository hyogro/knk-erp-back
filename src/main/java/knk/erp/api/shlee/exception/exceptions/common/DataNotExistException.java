package knk.erp.api.shlee.exception.exceptions.common;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class DataNotExistException extends CustomException {
    public DataNotExistException() {
        super(ExceptionCode.DATA_NOT_EXIST);
    }
}
