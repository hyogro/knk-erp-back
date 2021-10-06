package knk.erp.api.shlee.domain.schedule.service.exceptions.Schedule;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.domain.schedule.service.exceptions.CustomException;

public class AttendanceNotExistException extends CustomException {
    public AttendanceNotExistException() {
        super(ExceptionCode.ATTENDANCE_NOT_EXIST);
    }
}
