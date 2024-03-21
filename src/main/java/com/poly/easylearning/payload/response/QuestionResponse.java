package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private List<AnswerResponse> answers;
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
                .answers(question.getAnswers().stream().map(AnswerResponse::fromAnswer).toList())
                .questionTypeId(question.getQuestionType().getId())
                .description(question.getDescription())
                .weighted(question.getWeighted())
                .build();
    }

    public static QuestionResponse fromQuestion(Question question, List<AnswerResponse> answerResponses) {
        return QuestionResponse.builder()
                .id(question.getId())
                .createdDate(question.getCreatedDate())
                .createdBy(question.getCreatedBy())
                .lastModifiedDate(question.getLastModifiedDate())
                .lastModifiedBy(question.getLastModifiedBy())
                .title(question.getTitle())
                .answers(answerResponses)
                .questionTypeId(question.getQuestionType().getId())
                .description(question.getDescription())
                .weighted(question.getWeighted())
                .build();
    }
}
