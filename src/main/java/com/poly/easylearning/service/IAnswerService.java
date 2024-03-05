package com.poly.easylearning.service;

import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.AnswerRequest;
import com.poly.easylearning.payload.response.AnswerResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;
import java.util.List;

public interface IAnswerService {
    RestResponse<ListResponse<AnswerResponse>> getListAnswer(String keyword, PageRequest pageRequest);

    RestResponse<AnswerResponse> getOneAnswer(UUID id) throws DataNotFoundException;

    RestResponse<AnswerResponse> createAnswer(AnswerRequest answerRequest);

    RestResponse<AnswerResponse> updateAnswer(UUID id, AnswerRequest answerRequest) throws DataNotFoundException;

    void deleteOneAnswer(UUID id) throws DataNotFoundException;
    void deleteListAnswer(List<UUID> ids) throws DataNotFoundException;
}
