package com.hodolog.exception;

public class InvalidSigninInformation extends TopException {

    private static final String MESSAGE = "ID,PASSWORD가 올바르지 않습니다.";


    public InvalidSigninInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
