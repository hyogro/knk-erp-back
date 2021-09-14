package knk.erp.api.shlee.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import knk.erp.api.shlee.common.dto.ResponseData;
import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ExceptionPayload implements ResponseData {

    private LocalDateTime timestamp = LocalDateTime.now();

    private int status; // HTTP 상태 값 저장 400, 404, 500 등..

    private String message; //예외 메시지 저장

    private String code; // 예외를 세분화하기 위한 사용자 지정 코드,

    //@Valid의 Parameter 검증을 통과하지 못한 필드가 담긴다.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;

    static public ExceptionPayload create() {
        return new ExceptionPayload();
    }

    public ExceptionPayload code(String code) {
        this.code = code;
        return this;
    }

    public ExceptionPayload status(int status) {
        this.status = status;
        return this;
    }

    public ExceptionPayload message(String message) {
        this.message = message;
        return this;
    }

    public ExceptionPayload errors(Errors errors) {
        setCustomFieldErrors(errors.getFieldErrors());
        return this;
    }

    //BindingResult.getFieldErrors() 메소드를 통해 전달받은 fieldErrors
    public void setCustomFieldErrors(List<FieldError> fieldErrors) {

        customFieldErrors = new ArrayList<>();

        fieldErrors.forEach(error -> customFieldErrors.add(new CustomFieldError(
                Objects.requireNonNull(error.getCodes())[0],
                error.getRejectedValue(),
                error.getDefaultMessage()
        )));
    }

    //parameter 검증에 통과하지 못한 필드가 담긴 클래스이다.
    public static class CustomFieldError {

        private final String field;
        private final Object value;
        private final String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }

}
