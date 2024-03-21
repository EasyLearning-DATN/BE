package com.poly.easylearning.payload.request;

import com.poly.easylearning.constant.ResourceBundleConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class QuestionTypeRequest {
    @NotBlank(message = ResourceBundleConstant.QST_5005)
    @Size(min = 2, max = 255, message = "QST_5006")
    private String name;

    @NotBlank(message = ResourceBundleConstant.QST_5009)
    @Size(min = 1, max = 5, message = ResourceBundleConstant.QST_5010)
    private String code;
}
