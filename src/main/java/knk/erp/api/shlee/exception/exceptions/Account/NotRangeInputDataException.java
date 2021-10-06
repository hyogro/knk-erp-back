package knk.erp.api.shlee.exception.exceptions.Account;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class NotRangeInputDataException extends CustomException {
    public NotRangeInputDataException() { super(ExceptionCode.ACCOUNT_NOT_RANGE_INPUT_DATA); }
}
