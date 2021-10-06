package knk.erp.api.shlee.domain.schedule.service.exceptions.Schedule;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.domain.schedule.service.exceptions.CustomException;

public class AttendanceExistException extends CustomException {
    public AttendanceExistException() {
        super(ExceptionCode.ATTENDANCE_EXIST);
    }
}
