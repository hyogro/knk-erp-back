package knk.erp.api.shlee.exception.exceptions;

import knk.erp.api.shlee.exception.ExceptionCode;

public class RectifyAttendanceExistException extends CustomException{
    public RectifyAttendanceExistException() {
        super(ExceptionCode.RECTIFY_ATTENDANCE_EXIST);
    }
}
