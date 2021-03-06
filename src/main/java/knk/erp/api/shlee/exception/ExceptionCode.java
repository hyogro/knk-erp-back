package knk.erp.api.shlee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    //Common
    UNKNOWN_ERROR(500, "E9000", "UNKNOWN ERROR"),
    DATA_NOT_EXIST(400, "E9100", "can't found data"),
    INPUT_DATA_ERROR(400, "E9800", "필수 입력정보를 입력하지 않았거나 입력한 정보가 길이 제한 범위를 벗어납니다."),
    PERMISSION_DENIED(400, "E9900", "Permission Denied"),

    //Account E10**~12**
    NOT_FOUND_USER(400, "E1000", "Is Not Found User"),
    ALREADY_EXIST_ID(400, "E1100", "이미 존재하는 ID 입니다"),
    TARGET_IS_LEADER(400, "E1200", "대상이 부서의 리더입니다."),

    //Department E16**
    NOT_FOUND_DEPARTMENT(400, "E1600", "삭제되었거나 존재하지않는 부서입니다."),
    ALREADY_EXIST_DEPARTMENT(400, "E1601", "이미 존재하는 부서입니다."),
    NOT_BELONG_MEMBER(400, "E1602", "해당 부서에 소속되지않은 멤버입니다."),
    EXISTS_BELONG_MEMBER(400, "E1603", "해당 부서에 소속된 멤버가 존재합니다."),

    //Auth
    ACCESS_DENIED(403, "E3000", "Access Denied"),
    EXPIRED_TOKEN(403, "E3100", "Expired Token"),
    WRONG_TYPE_TOKEN(401, "E3110", "Wrong Type Token"),
    UNSUPPORTED_TOKEN(401, "E3120", "Unsupported Token"),
    WRONG_TOKEN(401, "E3130", "Illegal Argument Token"),
    WRONG_PASSWORD(401,"E3140","비밀번호 오류로 인한 토큰 생성 불가"),
    EXPIRED_REFRESH_TOKEN(403, "E3200", "Expired Refresh Token"),
    NOT_MATCHING_REFRESH_TOKEN(403, "E3300", "Not Matching Refresh Token"),
    NOT_MATCHING_ID_PASSWORD(403, "E3400", "Not Matching UserId Password"),
    VERIFY_NUMBER_NOT_MATCHING(403, "E3500", "Verify Number is Not Matching"),
    ENCRYPT_FAIL(403, "E3600", "Encrypt Fail"),

    //Fixtures E41**
    NOT_FOUND_FIXTURES_FORM(400, "E4100", "삭제되었거나 존재하지않는 비품요청서입니다."),
    NOT_FIXTURES_FORM_AUTHOR(400,"E4101","비품요청서 작성자가 아닙니다."),
    NOT_FOUND_FIXTURES(400, "E4102", "삭제되었거나 존재하지않는 비품요청항목입니다."),
    CHECKED_FIXTURES_FORM(400, "E4110", "이미 처리된 비품요청서입니다."),

    //Board E45**
    NOT_FOUND_BOARD(400,"E4500","삭제되었거나 존재하지않는 게시글입니다."),
    BOARD_NOT_AUTHOR(400,"E4501","게시글 작성자가 아닙니다."),

    //Schedule - Attendance E55**
    ATTENDANCE_EXIST(400, "E5501", "근태 정보가 존재합니다."),
    ATTENDANCE_NOT_EXIST(400, "E5502", "근태 정보가 존재하지 않습니다."),
    RECTIFY_ATTENDANCE_EXIST(400, "E5503", "근태 정정요청 정보가 존재합니다."),
    RECTIFY_ATTENDANCE_NOT_EXIST(400, "E5504", "근태 정정요청 정보가 존재하지 않습니다."),
    ATTENDANCE_OFF_WORK_EXIST(400, "E5505", "퇴근정보가 존재합니다."),

    //File E60**
    FILE_UPLOAD_ERROR(400, "E6001", "파일 업로드에 실패했습니다."),

    ;

    private final int status;
    private final String code;
    private final String message;



}
