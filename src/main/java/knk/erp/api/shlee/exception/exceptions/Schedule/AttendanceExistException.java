package knk.erp.api.shlee.exception.exceptions.schedule;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class AttendanceExistException extends CustomException {
    public AttendanceExistException() {
        super(ExceptionCode.ATTENDANCE_EXIST);
    }
}
