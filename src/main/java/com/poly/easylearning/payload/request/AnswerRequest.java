package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AnswerRequest {
    @NotBlank(message = "QST_5005")
    @Size(min = 1, max = 255, message = "QST_5006")
    private String value;

    @JsonProperty("is_correct")
    @NotNull(message = "")
    private Boolean isCorrect;
}
