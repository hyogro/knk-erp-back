package knk.erp.api.shlee.exception.exceptions.Department;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class DepartmentNotFoundException extends CustomException {
    public DepartmentNotFoundException() { super(ExceptionCode.NOT_FOUND_DEPARTMENT); }
}
