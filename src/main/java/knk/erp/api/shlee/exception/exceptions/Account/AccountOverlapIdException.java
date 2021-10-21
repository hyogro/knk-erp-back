package knk.erp.api.shlee.exception.exceptions.Account;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class AccountOverlapIdException extends CustomException {
    public AccountOverlapIdException() { super(ExceptionCode.ALREADY_EXIST_ID); }
}
