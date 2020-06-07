package com.leverx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessIsDeniedException extends RuntimeException {
    public AccessIsDeniedException() {
    }

    public AccessIsDeniedException(String message) {
        super(message);
    }

    public AccessIsDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessIsDeniedException(Throwable cause) {
        super(cause);
    }
}
