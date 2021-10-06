package knk.erp.api.shlee.domain.schedule.service.exceptions.Account;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.domain.schedule.service.exceptions.CustomException;

public class NotExistsInputDataException extends CustomException {
    public NotExistsInputDataException() { super(ExceptionCode.ACCOUNT_NOT_EXIST_INPUT_DATA); }
}
