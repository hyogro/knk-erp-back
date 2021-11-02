package knk.erp.api.shlee.exception.exceptions.Fixtures;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class FixturesFormNotFoundException extends CustomException {
    public FixturesFormNotFoundException() { super(ExceptionCode.NOT_FOUND_FIXTURES_FORM); }
}
