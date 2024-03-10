package com.max.api.v1.exception;

public class DomainTitleDuplicationException extends RuntimeException{
    public DomainTitleDuplicationException() {
        super();
    }

    public DomainTitleDuplicationException(String message) {
        super(message);
    }
}
