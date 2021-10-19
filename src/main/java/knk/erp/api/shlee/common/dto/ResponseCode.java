package knk.erp.api.shlee.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    /**
     * User A1***
     **/
    SIGNUP_USER_SUCCESS("A1000"),
    READ_USER_SUCCESS("A1100"),
    READ_DETAIL_USER_SUCCESS("A1110"),
    UPDATE_USER_INFO_SUCCESS("A1200"),
    UPDATE_USER_PASSWORD_SUCCESS("A1210"),
    UPDATE_USER_AUTHORITY_SUCCESS("A1220"),
    DELETE_USER_SUCCESS("A1300"),
    RECOVER_USER_SUCCESS("A1300"),
    CHECK_OVERLAP_ID_SUCCESS("A1400"),

    /**
     * Auth A2***
     **/
    LOGIN_USER_SUCCESS("A2000"),
    LOGOUT_USER_SUCCESS("A2100"),
    REFRESH_TOKEN_SUCCESS("A2200"),
    SEND_PHONE_CERT_SMS_SUCCESS("A2300"),
    VERIFY_PHONE_SUCCESS("A2400"),
    VERIFY_PHONE_FAIL("A2410"),
    SEND_EMAIL_CERT_SUCCESS("A2500"),
    VERIFY_EMAIL_SUCCESS("A2600"),
    VERIFY_EMAIL_FAIL("A2610"),


    /**
     * Schedule - Attendance A55**
     **/
    ON_WORK_SUCCESS("A5501"),
    OFF_WORK_SUCCESS("A5502"),
    READ_ATTENDANCE_SUCCESS("A5503"),
    CREATE_RECTIFY_ATTENDANCE_SUCCESS("A5504"),
    READ_RECTIFY_ATTENDANCE_SUCCESS("A5505"),
    DELETE_RECTIFY_ATTENDANCE_SUCCESS("A5506"),
    APPROVE_RECTIFY_ATTENDANCE_SUCCESS("A5507"),
    READ_ATTENDANCE_SUMMARY_SUCCESS("A5508"),
    READ_ATTENDANCE_TODAY_SUCCESS("A5509"),
    READ_ATTENDANCE_DUPLICATE_SUCCESS("A5510"),

    /**
     * Schedule - Schedule A56**
     **/
    CREATE_SCHEDULE_SUCCESS("A5601"),
    READ_SCHEDULE_SUCCESS("A5602"),
    UPDATE_SCHEDULE_SUCCESS("A5603"),
    DELETE_SCHEDULE_SUCCESS("A5604"),
    READ_ANNIVERSARY_SUCCESS("A5605"),


    /**
     * Schedule - Vacation A57**
     **/
    CREATE_ADD_VACATION_SUCCESS("A5701"),
    READ_ADD_VACATION_SUCCESS("A5702"),
    DELETE_ADD_VACATION_SUCCESS("A5704"),
    CREATE_VACATION_SUCCESS("A5711"),
    READ_VACATION_SUCCESS("A5712"),
    DELETE_VACATION_SUCCESS("A5714"),
    APPROVE_VACATION_SUCCESS("A5715"),
    REJECT_VACATION_SUCCESS("A5716"),

    /**
     * File A60**
     **/
    UPLOAD_FILE_SUCCESS("A6001"),



    ;
    private final String code;
}
