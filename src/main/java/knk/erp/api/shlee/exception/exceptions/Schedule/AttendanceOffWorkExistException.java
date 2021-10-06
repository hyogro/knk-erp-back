package knk.erp.api.shlee.exception.exceptions.schedule;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class AttendanceOffWorkExistException extends CustomException {
    public AttendanceOffWorkExistException() {
        super(ExceptionCode.ATTENDANCE_OFF_WORK_EXIST);
    }
}
