package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Answer;
import com.poly.easylearning.entity.QuestionType;
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
public class AnswerResponse extends BaseResponse {
    private String value;

    @JsonProperty("is_correct")
    private Boolean isCorrect;

    public static AnswerResponse fromAnswer(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .createdDate(answer.getCreatedDate())
                .createdBy(answer.getCreatedBy())
                .lastModifiedDate(answer.getLastModifiedDate())
                .lastModifiedBy(answer.getLastModifiedBy())
                .value(answer.getValue())
                .isCorrect(answer.getIsCorrect())
                .build();
    }
}
