package knk.erp.api.shlee.exception.exceptions.Account;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class AccountNotFoundMemberException extends CustomException {
    public AccountNotFoundMemberException() { super(ExceptionCode.NOT_FOUND_USER); }
}
