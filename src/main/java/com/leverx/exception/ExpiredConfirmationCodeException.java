package com.leverx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ExpiredConfirmationCodeException extends RuntimeException {
    public ExpiredConfirmationCodeException() {
    }

    public ExpiredConfirmationCodeException(String message) {
        super(message);
    }

    public ExpiredConfirmationCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredConfirmationCodeException(Throwable cause) {
        super(cause);
    }
}
