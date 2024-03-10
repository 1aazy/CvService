package com.max.api.v1.exception;

public class TestTitleDuplicationException extends RuntimeException {
    public TestTitleDuplicationException() {
        super();
    }

    public TestTitleDuplicationException(String message) {
        super(message);
    }
}
