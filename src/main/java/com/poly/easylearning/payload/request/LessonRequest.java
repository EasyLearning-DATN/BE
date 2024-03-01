package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class LessonRequest {
    @NotBlank(message = "LSN_4005")
    @Size(min = 2, max = 255, message = "LSN_4006")
    private String name;
    private String description;

    @JsonProperty("is_public")
    @NotNull(message = "LSN_4007")
    private boolean isPublic = true;

    @JsonProperty("image_url")
    private String imageUrl;
}
