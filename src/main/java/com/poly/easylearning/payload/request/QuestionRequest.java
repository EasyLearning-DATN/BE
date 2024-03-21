package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.constant.ResourceBundleConstant;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class QuestionRequest {
    private String title;
    private String description;
    @Min(value = 1, message = ResourceBundleConstant.QUE_7009)
    private Double weighted;
    private List<AnswerRequest> answers;
    @JsonProperty("lesson_id")
    private UUID lessonId;
    @JsonProperty("question_type_id")
    private UUID questionTypeId;
}
