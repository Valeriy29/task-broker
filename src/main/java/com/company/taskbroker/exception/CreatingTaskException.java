package com.company.taskbroker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CreatingTaskException extends RuntimeException {

    public CreatingTaskException() {
    }

    public CreatingTaskException(String message) {
        super(message);
    }

    public CreatingTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
