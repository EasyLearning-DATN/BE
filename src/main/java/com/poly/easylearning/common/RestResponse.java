package com.poly.easylearning.common;

import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@SuperBuilder(toBuilder = true)
public record RestResponse<T>(int status, T data) {
    public static <T> RestResponse<T> ok(T data) {
        return new RestResponse<>(HttpStatus.OK.value(), data);
    }

    public static <T> RestResponse<T> created(T data) {
        return new RestResponse<>(HttpStatus.CREATED.value(), data);
    }

    public static <T> RestResponse<T> accepted(T data) {
        return new RestResponse<>(HttpStatus.ACCEPTED.value(), data);
    }
}
