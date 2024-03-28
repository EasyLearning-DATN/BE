package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.entity.Question;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class GetListLessonResponse extends BaseResponse {
    public static final int DO_NOT_HAVE_QUESTION = 0;
    private String name;
    private String description;

    @JsonProperty("is_public")
    private boolean isPublic;

    private ImageResponse image;

    @JsonProperty("user_info")
    private UserInfoResponse userInfoResponse;

    @JsonProperty("access_times")
    private Integer accessTimes;

    @JsonProperty("number_of_question")
    private Integer numberOfQuestion;

    public static GetListLessonResponse fromLesson(Lesson lesson) {
        Collection<Question> questions = lesson.getQuestions();
        return GetListLessonResponse.builder()
                .id(lesson.getId())
                .createdDate(lesson.getCreatedDate())
                .createdBy(lesson.getCreatedBy())
                .lastModifiedDate(lesson.getLastModifiedDate())
                .lastModifiedBy(lesson.getLastModifiedBy())
                .name(lesson.getName())
                .description(lesson.getDescription())
                .isPublic(lesson.isPublic())
                .numberOfQuestion(questions!= null ? questions.size() : DO_NOT_HAVE_QUESTION)
                .accessTimes(lesson.getAccessTimes())
                .image(ImageResponse.fromImage(lesson.getImage()))
                .userInfoResponse(UserInfoResponse.fromUserInfo(lesson.getUserInfo()))
                .build();
    }
}
