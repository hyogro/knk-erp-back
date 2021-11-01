package knk.erp.api.shlee.exception.exceptions.Department;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class DepartmentOverlapException extends CustomException {
    public DepartmentOverlapException() {super(ExceptionCode.ALREADY_EXIST_DEPARTMENT);}
}
