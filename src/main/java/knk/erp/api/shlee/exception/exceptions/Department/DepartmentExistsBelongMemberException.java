package knk.erp.api.shlee.exception.exceptions.Department;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class DepartmentExistsBelongMemberException extends CustomException {
    public DepartmentExistsBelongMemberException() { super(ExceptionCode.EXISTS_BELONG_MEMBER); }
}
