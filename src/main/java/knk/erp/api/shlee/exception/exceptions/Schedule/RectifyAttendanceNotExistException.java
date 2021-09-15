package knk.erp.api.shlee.exception.exceptions.Schedule;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class RectifyAttendanceNotExistException extends CustomException {
    public RectifyAttendanceNotExistException() {
        super(ExceptionCode.RECTIFY_ATTENDANCE_NOT_EXIST);
    }
}
