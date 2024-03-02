package com.poly.easylearning.payload.response;

import com.poly.easylearning.entity.Lesson;
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
public class QuestionTypeResponse extends BaseResponse {
    private String name;

    public static QuestionTypeResponse fromQuestionType(QuestionType questionType) {
        QuestionTypeResponse questionTypeResponse = QuestionTypeResponse.builder()
                .id(questionType.getId())
                .createdDate(questionType.getCreatedDate())
                .createdBy(questionType.getCreatedBy())
                .lastModifiedDate(questionType.getLastModifiedDate())
                .lastModifiedBy(questionType.getLastModifiedBy())
                .name(questionType.getName())
                .build();

        return questionTypeResponse;
    }
}
