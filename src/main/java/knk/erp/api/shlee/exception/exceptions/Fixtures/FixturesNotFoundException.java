package knk.erp.api.shlee.exception.exceptions.Fixtures;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class FixturesNotFoundException extends CustomException {
    public FixturesNotFoundException() { super(ExceptionCode.NOT_FOUND_FIXTURES); }
}
