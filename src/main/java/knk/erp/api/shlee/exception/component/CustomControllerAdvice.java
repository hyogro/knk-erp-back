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

    /**
     * 파일
     * */

    //근태 정정요청 정보 존재하지 않음
    @ExceptionHandler(value = {FileIOException.class})
    public ResponseEntity<ExceptionPayload> handleFileIOException(FileIOException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * Account
     **/

    //삭제되었거나 없는 멤버 예외처리
    @ExceptionHandler(value = {AccountNotFoundMemberException.class})
    public ResponseEntity<ExceptionPayload> handleAccountNotFoundMemberException(AccountNotFoundMemberException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //회원가입 시 중복 ID 예외처리
    @ExceptionHandler(value = {AccountOverlapIdException.class})
    public ResponseEntity<ExceptionPayload> handleAccountOverlapIdException(AccountOverlapIdException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //유저 부서 수정 시 대상이 부서의 리더일 경우 예외처리
    @ExceptionHandler(value = {AccountTargetIsLeaderException.class})
    public ResponseEntity<ExceptionPayload> handleAccountTargetISLeaderException(AccountTargetIsLeaderException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //잘못된 비밀번호로 인한 토큰 생성 오류
    @ExceptionHandler(value = {AccountWrongPasswordException.class})
    public ResponseEntity<ExceptionPayload> handleAccountWrongPasswordException(AccountWrongPasswordException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Department
     **/

    //존재하지않는 부서 찾기 시 예외처리
    @ExceptionHandler(value = {DepartmentNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleDepartmentNotFoundException(DepartmentNotFoundException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //중복된 부서 이름 예외처리
    @ExceptionHandler(value = {DepartmentOverlapException.class})
    public ResponseEntity<ExceptionPayload> handleDepartmentOverlapException(DepartmentOverlapException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //부서에 존재하지않는 멤버 예외처리
    @ExceptionHandler(value = {DepartmentNotBelongMemberException.class})
    public ResponseEntity<ExceptionPayload> handleDepartmentNotBelongMemberException(DepartmentNotBelongMemberException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //부서 삭제 시, 부서에 속해있는 멤버가 존재할 경우 예외처리
    @ExceptionHandler(value = {DepartmentExistsBelongMemberException.class})
    public ResponseEntity<ExceptionPayload> handleDepartmentExistsBelongMemberException(DepartmentExistsBelongMemberException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fixtures
     **/

    //존재하지않거나 삭제된 비품요청서
    @ExceptionHandler(value = {FixturesFormNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleFixturesFormNotFoundException(FixturesFormNotFoundException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //작성자가 아닌 사람이 비품요청서 수정, 삭제 시도 예외처리
    @ExceptionHandler(value = {FixturesFormNotAuthorException.class})
    public ResponseEntity<ExceptionPayload> handleFixturesFormNotAuthorException(FixturesFormNotAuthorException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //이미 처리된 비품요청서 수정 시도, 삭제 예외처리
    @ExceptionHandler(value = {FixturesFormCheckedException.class})
    public ResponseEntity<ExceptionPayload> handleFixturesFormCheckedException(FixturesFormCheckedException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //존재하지않거나 삭제된 비품요청항목
    @ExceptionHandler(value = {FixturesNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleFixturesNotFoundException(FixturesNotFoundException e) {
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    /**
     * Board
     **/

    //존재하지않거나 삭제된 게시글 접근
    @ExceptionHandler(value = {BoardNotFoundException.class})
    public ResponseEntity<ExceptionPayload> handleBoardNotFoundException(BoardNotFoundException e){
        final ExceptionPayload payload = this.generateExceptionPayload(e);
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    //게시글 작성자가 아님
    @ExceptionHandler(value = {BoardNotAuthorException.class})
    public ResponseEntity<ExceptionPayload> handleBoardNotAuthorException(BoardNotAuthorException e){
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
