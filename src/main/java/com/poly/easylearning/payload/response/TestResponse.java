package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TestResponse extends BaseResponse {
    private String name;
    private String description;
    @JsonProperty("time_total")
    private Long timeTotal; //second
    @JsonProperty("time_question")
    private Long timeQuestion;
    @JsonProperty("total_question")
    private Integer totalQuestion;
    private ImageResponse image;
    @JsonProperty("view_result_type_id")
    private ViewResultTypeResponse viewResultType;

    @JsonProperty("question_tests")
    private Set<QuestionResponse> questions;

    @JsonProperty("user_info")
    private UserInfoResponse userInfoResponse;

    @JsonProperty("doing_time")
    private Integer doingTime;

    public static TestResponse fromTest(Test test) {
        return TestResponse.builder()
                .id(test.getId())
                .createdDate(test.getCreatedDate())
                .createdBy(test.getCreatedBy())
                .lastModifiedDate(test.getLastModifiedDate())
                .lastModifiedBy(test.getLastModifiedBy())
                .name(test.getName())
                .description(test.getDescription())
                .timeQuestion(test.getTimeQuestion())
                .timeTotal(test.getTimeTotal())
                .totalQuestion(test.getTotalQuestion())
                .image(ImageResponse.fromImage(test.getImage()))
                .viewResultType(ViewResultTypeResponse.fromViewResultType(test.getViewResultType()))
                .questions(test.getQuestionTests() != null ? test.getQuestionTests().stream().map(questionTest -> QuestionResponse.fromQuestion(questionTest.getQuestion())).collect(Collectors.toSet()): new HashSet<>())
                .userInfoResponse(UserInfoResponse.fromUserInfo(test.getUserInfo()))
                .doingTime(test.getDoingTime())
                .build();
    }
}
