package com.poly.easylearning.service;

import com.poly.easylearning.entity.QuestionTest;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.QuestionTestRequest;
import com.poly.easylearning.payload.request.QuestionTypeRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.QuestionTestResponse;
import com.poly.easylearning.payload.response.RestResponse;
import org.springframework.data.domain.PageRequest;

import java.util.Set;
import java.util.UUID;
import java.util.List;

public interface IQuestionTestService {
    RestResponse<ListResponse<QuestionTestResponse>> getListQuestionTest(PageRequest pageRequest);

    RestResponse<QuestionTestResponse> getOneQuestionTest(UUID id) throws DataNotFoundException;

    RestResponse<List<QuestionTestResponse>> createListQuestionTest(Set<UUID> ids, UUID testId);

    RestResponse<List<QuestionTestResponse>> updateListQuestionTest(Set<UUID> ids, UUID testId) throws DataNotFoundException;

    void deleteListQuestionTest(Set<UUID> questionIds, UUID testId) throws DataNotFoundException;
}
