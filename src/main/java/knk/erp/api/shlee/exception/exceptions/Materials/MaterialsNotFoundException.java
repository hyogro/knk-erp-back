package knk.erp.api.shlee.exception.exceptions.Materials;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class MaterialsNotFoundException extends CustomException {
    public MaterialsNotFoundException() { super(ExceptionCode.NOT_FOUND_MATERIALS); }
}
