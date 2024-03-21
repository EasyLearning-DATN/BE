package com.poly.easylearning.exception;

public record AppException(
        String code,
        Integer status,
        String message,
        long responseTime
) {
}
