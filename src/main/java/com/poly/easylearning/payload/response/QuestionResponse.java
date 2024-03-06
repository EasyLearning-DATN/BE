package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Answer;
import com.poly.easylearning.entity.Image;
import com.poly.easylearning.entity.Question;
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
public class QuestionResponse extends BaseResponse {
    private String title;
    private String description;
    private Double weighted;
    @JsonProperty("image_path")
    private Image image;
    private List<Answer> answers;
    @JsonProperty("question_type_id")
    private UUID questionTypeId;

    public static QuestionResponse fromQuestion(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .createdDate(question.getCreatedDate())
                .createdBy(question.getCreatedBy())
                .lastModifiedDate(question.getLastModifiedDate())
                .lastModifiedBy(question.getLastModifiedBy())
                .title(question.getTitle())
                .description(question.getDescription())
                .image(question.getImage())
                .weighted(question.getWeighted())
                .build();
    }
}
