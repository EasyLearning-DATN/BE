package com.poly.easylearning.service;

import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.LessonRequest;
import com.poly.easylearning.payload.request.QuestionTypeRequest;
import com.poly.easylearning.payload.response.LessonResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.QuestionTypeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IQuestionTypeService {
    RestResponse<ListResponse<QuestionTypeResponse>> getListQuestionType(String keyword, PageRequest pageRequest);

    RestResponse<QuestionTypeResponse> getOneQuestionType(UUID id) throws DataNotFoundException;

    RestResponse<QuestionTypeResponse> createQuestionType(QuestionTypeRequest questionTypeRequest);

    RestResponse<QuestionTypeResponse> updateQuestionType(UUID id, QuestionTypeRequest questionTypeRequest) throws DataNotFoundException;

    void deleteQuestionType(UUID id) throws DataNotFoundException;
}
