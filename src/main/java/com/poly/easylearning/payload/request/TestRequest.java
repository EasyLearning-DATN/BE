package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TestRequest {
    @NotBlank(message = ResourceBundleConstant.TES_10005)
    @Size(min = 1, max = 255, message = ResourceBundleConstant.TES_10006)
    private String name;
    private String description;
    @JsonProperty("time_total")
    private Long timeTotal; //second
    @Min(value = 3, message = ResourceBundleConstant.TES_10007)
    @JsonProperty("time_question")
    private Long timeQuestion;
    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("view_result_type_code")
    private String viewResultTypeCode;
    @JsonProperty("question_ids")
    private Set<UUID> questionIds;
}
