package com.poly.easylearning.service;

import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.AnswerRequest;
import com.poly.easylearning.payload.response.AnswerResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.UUID;
import java.util.List;

public interface IAnswerService {
    RestResponse<List<AnswerResponse>> createAllAnswer(List<AnswerRequest> answerRequests, UUID questionId);
}
