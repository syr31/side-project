package com.hodolog.exception;

public class InvalidPassword extends TopException {

    private static final String MESSAGE = "PASSWORD가 올바르지 않습니다.";


    public InvalidPassword() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
