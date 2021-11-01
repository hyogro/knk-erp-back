package knk.erp.api.shlee.exception.exceptions.Department;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class DepartmentNotBelongMemberException extends CustomException {
    public DepartmentNotBelongMemberException(){ super(ExceptionCode.NOT_BELONG_MEMBER); }
}
