package knk.erp.api.shlee.domain.schedule.service.exceptions.Schedule;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.domain.schedule.service.exceptions.CustomException;

public class AttendanceOffWorkExistException extends CustomException {
    public AttendanceOffWorkExistException() {
        super(ExceptionCode.ATTENDANCE_OFF_WORK_EXIST);
    }
}
