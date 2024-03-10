package com.poly.easylearning.exception;

import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException {

    private final String code;
    public InvalidUserException(String code) {
        this.code = code;
    }
}