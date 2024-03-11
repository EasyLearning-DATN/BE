package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.QuestionTest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class QuestionTestResponse {
    private UUID id;
    @JsonProperty("test")
    private TestResponse testResponse;
    @JsonProperty("question")
    private QuestionResponse questionResponse;

    public static QuestionTestResponse fromQuestionTest(QuestionTest questionTest) {
        return QuestionTestResponse.builder()
                .id(questionTest.getId())
                .testResponse(TestResponse.fromTest(questionTest.getTest()))
                .questionResponse(QuestionResponse.fromQuestion(questionTest.getQuestion()))
                .build();
    }
}
