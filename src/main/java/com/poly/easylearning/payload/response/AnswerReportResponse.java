package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.AnswerReport;
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
public class AnswerReportResponse {
    private String value;

    @JsonProperty("is_correct")
    private Boolean isCorrect;

    public static AnswerReportResponse fromAnswerReport(AnswerReport answerReport) {
        return AnswerReportResponse.builder()
                .value(answerReport.getValue())
                .isCorrect(answerReport.getIsCorrect())
                .build();
    }
}
