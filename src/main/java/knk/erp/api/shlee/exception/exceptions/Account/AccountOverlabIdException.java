package knk.erp.api.shlee.exception.exceptions.Account;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class AccountOverlabIdException extends CustomException {
    public AccountOverlabIdException() { super(ExceptionCode.ALREADY_EXIST_ID);}
}
