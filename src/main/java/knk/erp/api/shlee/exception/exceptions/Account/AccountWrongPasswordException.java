package knk.erp.api.shlee.exception.exceptions.Account;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class AccountWrongPasswordException extends CustomException {
    public AccountWrongPasswordException() { super(ExceptionCode.WRONG_PASSWORD); }
}
