package knk.erp.api.shlee.exception.exceptions;

import knk.erp.api.shlee.exception.ExceptionCode;

public class AttendanceExistException extends CustomException{
    public AttendanceExistException() {
        super(ExceptionCode.ATTENDANCE_EXIST);
    }
}
