package knk.erp.api.shlee.exception.exceptions.Board;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class BoardNotAuthorException extends CustomException {
    public BoardNotAuthorException() { super(ExceptionCode.BOARD_NOT_AUTHOR); }
}
