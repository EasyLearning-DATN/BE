package com.poly.easylearning.service;

import com.poly.easylearning.common.RestResponse;
import com.poly.easylearning.dto.LessonDTO;
import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.response.LessonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface ILessonService {
    RestResponse<Page<LessonResponse>> getListLesson(String keyword, PageRequest pageRequest);

    RestResponse<LessonResponse> getOneLesson(UUID id) throws DataNotFoundException;

    RestResponse<LessonResponse> createLesson(LessonDTO lessonDTO);

    RestResponse<LessonResponse> updateLesson(UUID id, LessonDTO lessonDTO) throws DataNotFoundException;

    void deleteLesson(UUID id) throws DataNotFoundException;
}
