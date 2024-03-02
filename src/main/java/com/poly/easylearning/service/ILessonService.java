package com.poly.easylearning.service;

import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.request.LessonRequest;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.response.LessonResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface ILessonService {
    RestResponse<ListResponse<LessonResponse>> getListLesson(String keyword, PageRequest pageRequest);

    RestResponse<LessonResponse> getOneLesson(UUID id) throws DataNotFoundException;

    RestResponse<LessonResponse> createLesson(LessonRequest lessonRequest);

    RestResponse<LessonResponse> updateLesson(UUID id, LessonRequest lessonRequest) throws DataNotFoundException;

    void deleteLesson(UUID id) throws DataNotFoundException;
}
