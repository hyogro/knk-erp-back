package knk.erp.api.shlee.exception.exceptions.schedule;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class RectifyAttendanceExistException extends CustomException {
    public RectifyAttendanceExistException() {
        super(ExceptionCode.RECTIFY_ATTENDANCE_EXIST);
    }
}
