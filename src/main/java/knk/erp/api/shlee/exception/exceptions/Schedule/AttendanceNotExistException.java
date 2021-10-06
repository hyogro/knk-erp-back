package knk.erp.api.shlee.exception.exceptions.schedule;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class AttendanceNotExistException extends CustomException {
    public AttendanceNotExistException() {
        super(ExceptionCode.ATTENDANCE_NOT_EXIST);
    }
}
