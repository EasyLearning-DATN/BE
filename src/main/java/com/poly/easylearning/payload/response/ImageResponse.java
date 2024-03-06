package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Image;
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
public class ImageResponse {
    @JsonProperty("public_id")
    private String publicId;

    private String url;

    public static ImageResponse fromImage(Image image) {
        ImageResponse imageResponse = ImageResponse.builder()
                .publicId(image.getPublicId())
                .url(image.getUrl())
                .build();

        return imageResponse;
    }
}
