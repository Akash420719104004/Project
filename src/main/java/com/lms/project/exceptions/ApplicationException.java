package com.lms.project.exceptions;
import lombok.Getter;
@Getter
public class ApplicationException extends Exception {
    private static final long serialVersionUID = 2816438424415940868L;
    private final ErrorCode errorCode;
    public ApplicationException(ErrorCode codes) {
        super(getMessage(codes));
        this.errorCode = codes;
    }
    public ApplicationException(ErrorCode codes, String message) {
        super(message);
        this.errorCode = codes;
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }
    private static String getMessage(ErrorCode errorCode) {
        if (errorCode.getMessage() != null) {
            return errorCode.getMessage();
        }
        else {
            return null;
        }
    }
}