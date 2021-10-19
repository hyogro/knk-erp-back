package knk.erp.api.shlee.exception.component;

import knk.erp.api.shlee.exception.ExceptionPayload;
import knk.erp.api.shlee.exception.exceptions.*;
import knk.erp.api.shlee.exception.exceptions.common.ErrorInputDataException;
import knk.erp.api.shlee.exception.exceptions.attendance.*;
import knk.erp.api.shlee.exception.exceptions.common.DataNotExistException;
import knk.erp.api.shlee.exception.exceptions.common.PermissionDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvice {

    /**
     * 공통
     * */
    //EntityNotFoundException 예외처리
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleEntityNotFoundException(EntityNotFoundException e) {
        DataNotExistException exception = new DataNotExistException();
        final ExceptionPayload payload = this.generateExceptionPayload(exception);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }
    //권한 없음
    @ExceptionHandler(value = {PermissionDeniedException.class})
    public ResponseEntity<ExceptionPayload> handlePermissionDeniedException(PermissionDeniedException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //필수 입력값을 입력하지않음
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionPayload> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorInputDataException exception = new ErrorInputDataException();
        final ExceptionPayload payload = this.generateExceptionPayload(exception);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * 근태정보
     * */
    //근태 정보 이미 존재함.
    @ExceptionHandler(value = {AttendanceExistException.class})
    public ResponseEntity<ExceptionPayload> handleAttendanceExistException(AttendanceExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //근태 정보 존재하지 않음
    @ExceptionHandler(value = {AttendanceNotExistException.class})
    public ResponseEntity<ExceptionPayload> handleAttendanceNotExistException(AttendanceNotExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //근태 정정요청 정보 이미 존재함.
    @ExceptionHandler(value = {RectifyAttendanceExistException.class})
    public ResponseEntity<ExceptionPayload> handleRectifyAttendanceExistException(RectifyAttendanceExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //근태 정정요청 정보 존재하지 않음
    @ExceptionHandler(value = {RectifyAttendanceNotExistException.class})
    public ResponseEntity<ExceptionPayload> handleRectifyAttendanceNotExistException(RectifyAttendanceNotExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //근태 정정요청 정보 존재하지 않음
    @ExceptionHandler(value = {AttendanceOffWorkExistException.class})
    public ResponseEntity<ExceptionPayload> handleAttendanceOffWorkExistException(AttendanceOffWorkExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }


    //payload 생성 메서드
    private ExceptionPayload generateExceptionPayload(CustomException e){
        return ExceptionPayload
                .create()
                .status(e.getExceptionCode().getStatus())
                .code(e.getExceptionCode().getCode())
                .message(e.getMessage());
    }
}
