package com.poly.easylearning.service;

import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.payload.response.GetOneLessonResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.request.LessonRequest;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.response.GetListLessonResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface ILessonService {
    RestResponse<ListResponse<GetListLessonResponse>> getListLesson(String keyword, String id, String dateStart, String dateEnd, String createdBy, String isPublic, PageRequest pageRequest);

    RestResponse<GetListLessonResponse> getOneLesson(UUID id) throws DataNotFoundException;

    public RestResponse<GetOneLessonResponse> getOneLessonIncrementAccess(UUID id) throws DataNotFoundException;


    RestResponse<GetListLessonResponse> createLesson(LessonRequest lessonRequest);

    RestResponse<GetListLessonResponse> updateLesson(UUID id, LessonRequest lessonRequest) throws DataNotFoundException;

    void deleteLesson(UUID id) throws DataNotFoundException;

    Lesson findLessonEntityById(UUID lessonID);
}
