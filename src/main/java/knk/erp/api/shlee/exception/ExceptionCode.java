package knk.erp.api.shlee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    //Common
    UNKNOWN_ERROR(500, "E9000", "UNKNOWN ERROR"),
    DATA_NOT_EXIST(400, "E9100", "can't found data"),
    PERMISSION_DENIED(400, "E9900", "Permission Denied"),


    //Auth
    ACCESS_DENIED(403, "E3000", "Access Denied"),
    EXPIRED_TOKEN(403, "E3100", "Expired Token"),
    WRONG_TYPE_TOKEN(401, "E3110", "Wrong Type Token"),
    UNSUPPORTED_TOKEN(401, "E3120", "Unsupported Token"),
    WRONG_TOKEN(401, "E3130", "Illegal Argument Token"),
    EXPIRED_REFRESH_TOKEN(403, "E3200", "Expired Refresh Token"),
    NOT_MATCHING_REFRESH_TOKEN(403, "E3300", "Not Matching Refresh Token"),
    NOT_MATCHING_ID_PASSWORD(403, "E3400", "Not Matching UserId Password"),
    VERIFY_NUMBER_NOT_MATCHING(403, "E3500", "Verify Number is Not Matching"),
    ENCRYPT_FAIL(403, "E3600", "Encrypt Fail"),

    //Account
    NOT_FOUND_USER(400, "E1000", "Not Found User"),
    ALREADY_EXIST_USER(409, "E1100", "Already Exist User"),

    //Schedule - Attendance E55**
    ATTENDANCE_EXIST(400, "E5501", "근태 정보가 존재합니다."),
    ATTENDANCE_NOT_EXIST(400, "E5502", "근태 정보가 존재하지 않습니다."),
    RECTIFY_ATTENDANCE_EXIST(400, "E5503", "근태 정정요청 정보가 존재합니다."),
    RECTIFY_ATTENDANCE_NOT_EXIST(400, "E5504", "근태 정정요청 정보가 존재하지 않습니다."),
    ATTENDANCE_OFF_WORK_EXIST(400, "E5505", "퇴근정보가 존재합니다."),

    ;

    private final int status;
    private final String code;
    private final String message;



}
