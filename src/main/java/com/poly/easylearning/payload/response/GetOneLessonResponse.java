package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Lesson;
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
public class GetOneLessonResponse extends BaseResponse {
    private String name;
    private String description;

    @JsonProperty("is_public")
    private boolean isPublic;

    private ImageResponse image;

    @JsonProperty("user_info")
    private UserInfoResponse userInfoResponse;

    @JsonProperty("access_times")
    private Integer accessTimes;

    @JsonProperty("questions")
    private List<QuestionResponse> questionResponses;

    public static GetOneLessonResponse fromLesson(Lesson lesson) {
        return GetOneLessonResponse.builder()
                .id(lesson.getId())
                .createdDate(lesson.getCreatedDate())
                .createdBy(lesson.getCreatedBy())
                .lastModifiedDate(lesson.getLastModifiedDate())
                .lastModifiedBy(lesson.getLastModifiedBy())
                .name(lesson.getName())
                .description(lesson.getDescription())
                .isPublic(lesson.isPublic())
                .accessTimes(lesson.getAccessTimes())
                .questionResponses(lesson.getQuestions().stream().map(QuestionResponse::fromQuestion).toList())
                .image(ImageResponse.fromImage(lesson.getImage()))
                .userInfoResponse(UserInfoResponse.fromUserInfo(lesson.getUserInfo()))
                .build();
    }
}
