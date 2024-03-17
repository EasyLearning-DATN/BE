package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.entity.QuestionReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    private String[] answerOfUsers;
    @JsonProperty("question_type_code")
    private String questionTypeCode;
    private double point;
    public static QuestionReportResponse fromQuestionReport(QuestionReport questionReport) {
        return QuestionReportResponse.builder()
                .title(questionReport.getTitle())
                .answers(questionReport.getAnswerReports().stream().map(AnswerReportResponse::fromAnswerReport).toList())
                .answerOfUsers(questionReport.getAnswerOfUser().split(DefaultValueConstants.JOIN_CHARACTER))
                .questionTypeCode(questionReport.getQuestionTypeCode())
                .description(questionReport.getDescription())
                .point(questionReport.getPoint())
                .weighted(questionReport.getWeighted())
                .build();
    }
}
