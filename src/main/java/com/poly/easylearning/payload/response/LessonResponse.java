package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Lesson;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class LessonResponse extends BaseResponse {
    private String name;
    private String description;

    @JsonProperty("is_public")
    private boolean isPublic;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("user_info")
    private UserInfoResponse userInfoResponse;

    public static LessonResponse fromLesson(Lesson lesson) {
        LessonResponse lessonResponse = LessonResponse.builder()
                .id(lesson.getId())
                .createdDate(lesson.getCreatedDate())
                .createdBy(lesson.getCreatedBy())
                .lastModifiedDate(lesson.getLastModifiedDate())
                .lastModifiedBy(lesson.getLastModifiedBy())
                .name(lesson.getName())
                .description(lesson.getDescription())
                .isPublic(lesson.isPublic())
                .imageUrl(lesson.getImageUrl())
                .userInfoResponse(UserInfoResponse.fromUserInfo(lesson.getUserInfo()))
                .build();

        return lessonResponse;
    }
}
