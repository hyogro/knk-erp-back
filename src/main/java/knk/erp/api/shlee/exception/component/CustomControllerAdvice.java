package knk.erp.api.shlee.exception.component;

import knk.erp.api.shlee.exception.ExceptionPayload;
import knk.erp.api.shlee.exception.exceptions.Account.AccountNotFoundMemberException;
import knk.erp.api.shlee.exception.exceptions.Account.AccountOverlapIdException;
import knk.erp.api.shlee.exception.exceptions.Account.AccountTargetIsLeaderException;
import knk.erp.api.shlee.exception.exceptions.Account.AccountWrongPasswordException;
import knk.erp.api.shlee.exception.exceptions.Board.BoardNotAuthorException;
import knk.erp.api.shlee.exception.exceptions.Board.BoardNotFoundException;
import knk.erp.api.shlee.exception.exceptions.CustomException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentExistsBelongMemberException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentNotBelongMemberException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentNotFoundException;
import knk.erp.api.shlee.exception.exceptions.Department.DepartmentOverlapException;
import knk.erp.api.shlee.exception.exceptions.Fixtures.FixturesFormCheckedException;
import knk.erp.api.shlee.exception.exceptions.Fixtures.FixturesFormNotAuthorException;
import knk.erp.api.shlee.exception.exceptions.Fixtures.FixturesFormNotFoundException;
import knk.erp.api.shlee.exception.exceptions.Fixtures.FixturesNotFoundException;
import knk.erp.api.shlee.exception.exceptions.attendance.*;
import knk.erp.api.shlee.exception.exceptions.common.DataNotExistException;
import knk.erp.api.shlee.exception.exceptions.common.ErrorInputDataException;
import knk.erp.api.shlee.exception.exceptions.common.PermissionDeniedException;
import knk.erp.api.shlee.exception.exceptions.file.FileIOException;
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
     * ??????
     * */
    //EntityNotFoundException ????????????
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleEntityNotFoundException(EntityNotFoundException e) {
        DataNotExistException exception = new DataNotExistException();
        final ExceptionPayload payload = this.generateExceptionPayload(exception);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }
    //?????? ??????
    @ExceptionHandler(value = {PermissionDeniedException.class})
    public ResponseEntity<ExceptionPayload> handlePermissionDeniedException(PermissionDeniedException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //?????? ???????????? ??????????????????
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionPayload> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorInputDataException exception = new ErrorInputDataException();
        final ExceptionPayload payload = this.generateExceptionPayload(exception);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * ????????????
     * */
    //?????? ?????? ?????? ?????????.
    @ExceptionHandler(value = {AttendanceExistException.class})
    public ResponseEntity<ExceptionPayload> handleAttendanceExistException(AttendanceExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //?????? ?????? ???????????? ??????
    @ExceptionHandler(value = {AttendanceNotExistException.class})
    public ResponseEntity<ExceptionPayload> handleAttendanceNotExistException(AttendanceNotExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //?????? ???????????? ?????? ?????? ?????????.
    @ExceptionHandler(value = {RectifyAttendanceExistException.class})
    public ResponseEntity<ExceptionPayload> handleRectifyAttendanceExistException(RectifyAttendanceExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //?????? ???????????? ?????? ???????????? ??????
    @ExceptionHandler(value = {RectifyAttendanceNotExistException.class})
    public ResponseEntity<ExceptionPayload> handleRectifyAttendanceNotExistException(RectifyAttendanceNotExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //?????? ???????????? ?????? ???????????? ??????
    @ExceptionHandler(value = {AttendanceOffWorkExistException.class})
    public ResponseEntity<ExceptionPayload> handleAttendanceOffWorkExistException(AttendanceOffWorkExistException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * ??????
     * */

    //?????? ???????????? ?????? ???????????? ??????
    @ExceptionHandler(value = {FileIOException.class})
    public ResponseEntity<ExceptionPayload> handleFileIOException(FileIOException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * Account
     **/

    //?????????????????? ?????? ?????? ????????????
    @ExceptionHandler(value = {AccountNotFoundMemberException.class})
    public ResponseEntity<ExceptionPayload> handleAccountNotFoundMemberException(AccountNotFoundMemberException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //???????????? ??? ?????? ID ????????????
    @ExceptionHandler(value = {AccountOverlapIdException.class})
    public ResponseEntity<ExceptionPayload> handleAccountOverlapIdException(AccountOverlapIdException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //?????? ?????? ?????? ??? ????????? ????????? ????????? ?????? ????????????
    @ExceptionHandler(value = {AccountTargetIsLeaderException.class})
    public ResponseEntity<ExceptionPayload> handleAccountTargetISLeaderException(AccountTargetIsLeaderException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //????????? ??????????????? ?????? ?????? ?????? ??????
    @ExceptionHandler(value = {AccountWrongPasswordException.class})
    public ResponseEntity<ExceptionPayload> handleAccountWrongPasswordException(AccountWrongPasswordException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Department
     **/

    //?????????????????? ?????? ?????? ??? ????????????
    @ExceptionHandler(value = {DepartmentNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleDepartmentNotFoundException(DepartmentNotFoundException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //????????? ?????? ?????? ????????????
    @ExceptionHandler(value = {DepartmentOverlapException.class})
    public ResponseEntity<ExceptionPayload> handleDepartmentOverlapException(DepartmentOverlapException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //????????? ?????????????????? ?????? ????????????
    @ExceptionHandler(value = {DepartmentNotBelongMemberException.class})
    public ResponseEntity<ExceptionPayload> handleDepartmentNotBelongMemberException(DepartmentNotBelongMemberException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //?????? ?????? ???, ????????? ???????????? ????????? ????????? ?????? ????????????
    @ExceptionHandler(value = {DepartmentExistsBelongMemberException.class})
    public ResponseEntity<ExceptionPayload> handleDepartmentExistsBelongMemberException(DepartmentExistsBelongMemberException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fixtures
     **/

    //????????????????????? ????????? ???????????????
    @ExceptionHandler(value = {FixturesFormNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleFixturesFormNotFoundException(FixturesFormNotFoundException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //???????????? ?????? ????????? ??????????????? ??????, ?????? ?????? ????????????
    @ExceptionHandler(value = {FixturesFormNotAuthorException.class})
    public ResponseEntity<ExceptionPayload> handleFixturesFormNotAuthorException(FixturesFormNotAuthorException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //?????? ????????? ??????????????? ?????? ??????, ?????? ????????????
    @ExceptionHandler(value = {FixturesFormCheckedException.class})
    public ResponseEntity<ExceptionPayload> handleFixturesFormCheckedException(FixturesFormCheckedException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //????????????????????? ????????? ??????????????????
    @ExceptionHandler(value = {FixturesNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleFixturesNotFoundException(FixturesNotFoundException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * Board
     **/

    //????????????????????? ????????? ????????? ??????
    @ExceptionHandler(value = {BoardNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleBoardNotFoundException(BoardNotFoundException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //????????? ???????????? ??????
    @ExceptionHandler(value = {BoardNotAuthorException.class})
    public ResponseEntity<ExceptionPayload> handleBoardNotAuthorException(BoardNotAuthorException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }




    //payload ?????? ?????????
    private ExceptionPayload generateExceptionPayload(CustomException e){
        return ExceptionPayload
                .create()
                .status(e.getExceptionCode().getStatus())
                .code(e.getExceptionCode().getCode())
                .message(e.getMessage());
    }
}
