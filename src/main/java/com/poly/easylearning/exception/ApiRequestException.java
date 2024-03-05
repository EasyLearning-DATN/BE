package com.poly.easylearning.exception;

import lombok.Getter;

@Getter
public class ApiRequestException extends RuntimeException {

    private final String code;
    public ApiRequestException(String code) {
        this.code = code;
    }
}

