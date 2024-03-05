package com.poly.easylearning.exception;

import lombok.Getter;

@Getter
public class EnumAppNotFoundException extends RuntimeException {

    private final String code;

    public EnumAppNotFoundException(String code) {
        this.code = code;
    }
}