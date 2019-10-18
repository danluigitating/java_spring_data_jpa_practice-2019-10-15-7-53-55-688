package com.tw.apistackbase.error;

import javassist.NotFoundException;

public class CustomError {

    private int errorCode;
    private String errorMessage;

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
