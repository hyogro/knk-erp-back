package knk.erp.api.shlee.exception.exceptions.Fixtures;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class FixturesFormNotAuthorException extends CustomException {
    public FixturesFormNotAuthorException() { super(ExceptionCode.NOT_FIXTURES_FORM_AUTHOR); }
}
