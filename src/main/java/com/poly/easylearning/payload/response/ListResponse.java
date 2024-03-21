package com.poly.easylearning.payload.response;

import com.poly.easylearning.utils.ResponseUtil;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

//@SuperBuilder(toBuilder = true)
public record ListResponse<T>(int totalPage, List<T> data) {

    public static <T> ListResponse<T> build(int totalPage, List<T> data) {
        return new ListResponse<>(totalPage, data);
    }
}
