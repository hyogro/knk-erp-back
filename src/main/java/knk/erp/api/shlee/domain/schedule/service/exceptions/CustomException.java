package knk.erp.api.shlee.domain.schedule.service.exceptions;

import knk.erp.api.shlee.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}