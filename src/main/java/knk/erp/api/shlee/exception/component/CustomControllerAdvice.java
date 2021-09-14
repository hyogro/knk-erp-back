package knk.erp.api.shlee.exception.component;

import knk.erp.api.shlee.exception.ExceptionPayload;
import knk.erp.api.shlee.exception.exceptions.AttendanceExistException;
import knk.erp.api.shlee.exception.exceptions.AttendanceNotExistException;
import knk.erp.api.shlee.exception.exceptions.RectifyAttendanceExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvice {

    //근태 정보 이미 존재함.
    @ExceptionHandler(value = {AttendanceExistException.class})
    public ResponseEntity<ExceptionPayload> handleAttendanceExistException(AttendanceExistException e) {
        final ExceptionPayload payload = ExceptionPayload
                .create()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(e.getExceptionCode().getCode())
                .message(e.getMessage());
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //근태 정보 존재하지 않음
    @ExceptionHandler(value = {AttendanceNotExistException.class})
    public ResponseEntity<ExceptionPayload> handleAttendanceNotExistException(AttendanceNotExistException e) {
        final ExceptionPayload payload = ExceptionPayload
                .create()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(e.getExceptionCode().getCode())
                .message(e.getMessage());
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //근태 정정요청 정보 이미 존재함.
    @ExceptionHandler(value = {RectifyAttendanceExistException.class})
    public ResponseEntity<ExceptionPayload> handleRectifyAttendanceExistException(RectifyAttendanceExistException e) {
        final ExceptionPayload payload = ExceptionPayload
                .create()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(e.getExceptionCode().getCode())
                .message(e.getMessage());
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //근태 정정요청 정보 존재하지 않음
    @ExceptionHandler(value = {RectifyAttendanceExistException.class})
    public ResponseEntity<ExceptionPayload> handleARectifyAttendanceExistException(RectifyAttendanceExistException e) {
        final ExceptionPayload payload = ExceptionPayload
                .create()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(e.getExceptionCode().getCode())
                .message(e.getMessage());
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }
}
