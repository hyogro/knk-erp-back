package knk.erp.api.shlee.exception.exceptions;

import knk.erp.api.shlee.exception.ExceptionCode;

public class AttendanceNotExistException extends CustomException{
    public AttendanceNotExistException() {
        super(ExceptionCode.ATTENDANCE_NOT_EXIST);
    }
}
