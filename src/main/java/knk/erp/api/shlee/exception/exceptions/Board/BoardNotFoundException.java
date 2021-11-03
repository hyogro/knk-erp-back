package knk.erp.api.shlee.exception.exceptions.Board;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class BoardNotFoundException extends CustomException {
    public BoardNotFoundException() { super(ExceptionCode.NOT_FOUND_BOARD); }
}
