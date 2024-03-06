package com.poly.easylearning.payload.response;

import com.poly.easylearning.utils.ResponseUtil;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

//@SuperBuilder(toBuilder = true)
public record RestResponse<T>(int status, String code, String message, T data) {

    public static <T> RestResponse<T> ok(String code, T data) {
        return new RestResponse<>(HttpStatus.OK.value(), code, ResponseUtil.getMessageBundle(code), data);
    }
    public static <T> RestResponse<T> created(String code,T data) {
        return new RestResponse<>(HttpStatus.CREATED.value(), code, ResponseUtil.getMessageBundle(code), data);
    }

    public static <T> RestResponse<T> accepted(String code, T data) {
        return new RestResponse<>(HttpStatus.ACCEPTED.value(), code, ResponseUtil.getMessageBundle(code), data);
    }
}
