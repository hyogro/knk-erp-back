package knk.erp.api.shlee.exception.exceptions.Account;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class AccountTargetIsLeaderException extends CustomException {
    public AccountTargetIsLeaderException() { super(ExceptionCode.TARGET_IS_LEADER); }
}
