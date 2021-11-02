package knk.erp.api.shlee.exception.exceptions.Fixtures;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class FixturesFormCheckedException extends CustomException {
    public FixturesFormCheckedException() { super(ExceptionCode.CHECKED_FIXTURES_FORM); }
}
