package knk.erp.api.shlee.exception.exceptions.file;

import knk.erp.api.shlee.exception.ExceptionCode;
import knk.erp.api.shlee.exception.exceptions.CustomException;

public class FileIOException extends CustomException {
    public FileIOException() {
        super(ExceptionCode.FILE_UPLOAD_ERROR);
    }
}
