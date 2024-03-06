package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.constant.ResourceBundleConstant;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
public class ImageRequest {
    @NotBlank(message = ResourceBundleConstant.IMG_3002)
    @JsonProperty("public_id")
    private String publicId;

    @NotBlank(message = ResourceBundleConstant.IMG_3003)
    private String url;
}
