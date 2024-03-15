package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Question;
import com.poly.easylearning.entity.QuestionReport;
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
public class QuestionReportResponse {
    private String title;
    private String description;
    private Double weighted;
    private List<AnswerReportResponse> answers;
    @JsonProperty("answer_of_user")
    private String answerOfUser;
    @JsonProperty("question_type_code")
    private String questionTypeCode;

    public static QuestionReportResponse fromQuestionReport(QuestionReport questionReport) {
        return QuestionReportResponse.builder()
                .title(questionReport.getTitle())
                .answers(questionReport.getAnswerReports().stream().map(AnswerReportResponse::fromAnswerReport).toList())
                .answerOfUser(questionReport.getAnswerOfUser())
                .questionTypeCode(questionReport.getQuestionTypeCode())
                .description(questionReport.getDescription())
                .weighted(questionReport.getWeighted())
                .build();
    }
}
