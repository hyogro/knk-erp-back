package knk.erp.api.shlee.exception.exceptions;

import knk.erp.api.shlee.exception.ExceptionCode;

public class RectifyAttendanceNotExistException extends CustomException{
    public RectifyAttendanceNotExistException() {
        super(ExceptionCode.RECTIFY_ATTENDANCE_NOT_EXIST);
    }
}
