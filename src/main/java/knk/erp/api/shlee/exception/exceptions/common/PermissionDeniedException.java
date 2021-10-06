package knk.erp.api.shlee.exception.exceptions.common;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class PermissionDeniedException extends CustomException {
    public PermissionDeniedException() {
        super(ExceptionCode.PERMISSION_DENIED);
    }
}
