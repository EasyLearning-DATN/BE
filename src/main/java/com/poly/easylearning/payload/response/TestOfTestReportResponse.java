package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Test;
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
public class TestOfTestReportResponse {
    private String name;
    @JsonProperty("time_total")
    private Long timeTotal; //second
    @JsonProperty("time_question")
    private Long timeQuestion;
    @JsonProperty("total_question")
    private Integer totalQuestion;
    private ImageResponse image;

    public static TestOfTestReportResponse fromTest(Test test) {
        return TestOfTestReportResponse.builder()
                .name(test.getName())
                .timeQuestion(test.getTimeQuestion())
                .timeTotal(test.getTimeTotal())
                .totalQuestion(test.getTotalQuestion())
                .image(ImageResponse.fromImage(test.getImage()))
                .build();
    }
}
