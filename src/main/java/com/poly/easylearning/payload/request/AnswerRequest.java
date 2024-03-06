package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.constant.ResourceBundleConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class AnswerRequest {
    @NotBlank(message = ResourceBundleConstant.ANS_6005)
    @Size(min = 1, max = 255, message = ResourceBundleConstant.ANS_6006)
    private String value;

    @JsonProperty("is_correct")
    @NotNull(message = ResourceBundleConstant.ANS_6007)
    private Boolean isCorrect;

    @JsonProperty("question_id")
    private UUID questionId;
}
