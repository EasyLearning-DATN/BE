package com.poly.easylearning.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {

    private final String code;
    public DataNotFoundException(String code) {
        this.code = code;
    }
}