package com.poly.easylearning.service;

import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.QuestionRequest;
import com.poly.easylearning.payload.request.QuestionTypeRequest;
import com.poly.easylearning.payload.response.*;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IQuestionService {
    RestResponse<ListResponse<QuestionResponse>> getListQuestion(String keyword, String id, String dateStart, String dateEnd, String createdBy, String lessonId, PageRequest pageRequest);

    RestResponse<QuestionResponse> getOneQuestion(UUID id) throws DataNotFoundException;

    RestResponse<QuestionResponse> createQuestion(QuestionRequest questionRequest);

    RestResponse<QuestionResponse> updateQuestion(UUID id, QuestionRequest questionRequest) throws DataNotFoundException;

    void deleteQuestion(UUID id) throws DataNotFoundException;
}
