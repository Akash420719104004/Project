package com.lms.project.exceptions;
public enum ErrorCode implements ErrorHandle {
    CAP_1001("1001", "Invalid Input"),
    CAP_1002("1000", "Invalid Input1"),
    CAP_1003("1003", "INVALID_CREDENTIALS"),
    CAP_1016("1016", "INVALID_CREDENTIALS"),
    CAP_1017("1017", "Email Id Already Registered"),
    CAP_1018("1017", "Mobile No Already Registered"),
    CAP_10039("CAP_10039","user Not Found"),
    CAP_1004("CAP_1002","JWT related error");
    private final String errorCode;
    private final String message;
    ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
    @Override
    public String getErrorCode() {
        return this.errorCode;
    }
    @Override
    public String getMessage() {
        return this.message;
    }
    public int getHttpStatus() {
        return 0;
    }
}