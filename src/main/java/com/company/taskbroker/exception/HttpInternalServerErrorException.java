package com.company.taskbroker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public final class HttpInternalServerErrorException extends RuntimeException {

    public HttpInternalServerErrorException() {
    }

    public HttpInternalServerErrorException(String message) {
        super(message);
    }

    public HttpInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}