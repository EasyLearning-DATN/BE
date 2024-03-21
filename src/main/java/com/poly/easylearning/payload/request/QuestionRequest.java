package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class QuestionRequest {
    private String title;
    private String description;

    @JsonProperty("image_path")
    private String imagePath;

    private Double weighted;
}
