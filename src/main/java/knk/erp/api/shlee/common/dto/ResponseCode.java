package knk.erp.api.shlee.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    /**
     * User A1***
     **/
    CREATE_USER_SUCCESS("A1000"),
    READ_USER_SUCCESS("A1100"),
    UPDATE_USER_INFO_SUCCESS("A1200"),
    UPDATE_USER_PASSWORD_SUCCESS("A1210"),
    UPDATE_USER_AUTHORITY_SUCCESS("A1220"),
    DELETE_USER_SUCCESS("A1300"),
    RECOVER_USER_SUCCESS("A1300"),

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


    ;
    private final String code;
}
