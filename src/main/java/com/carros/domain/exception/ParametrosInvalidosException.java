package com.carros.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ParametrosInvalidosException extends RuntimeException {
    public ParametrosInvalidosException(String s) {
        super(s);
    }
}
