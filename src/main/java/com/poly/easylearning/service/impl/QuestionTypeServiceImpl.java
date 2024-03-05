package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.QuestionType;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.QuestionTypeRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.QuestionTypeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IQuestionTypeRepo;
import com.poly.easylearning.service.IQuestionTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class QuestionTypeServiceImpl implements IQuestionTypeService {
    private final IQuestionTypeRepo questionTypeRepo;

    @Override
    public RestResponse<ListResponse<QuestionTypeResponse>> getListQuestionType(String keyword, PageRequest pageRequest) {
        Page<QuestionType> pageReponse = questionTypeRepo.searchQuestionType(keyword, pageRequest);
        List<QuestionTypeResponse> questionTypeResponses = pageReponse.get().map(QuestionTypeResponse::fromQuestionType).toList();
        ListResponse<QuestionTypeResponse> listResponse = ListResponse.build(pageReponse.getTotalPages(), questionTypeResponses);
        return RestResponse.ok(ResourceBundleConstant.QST_5003,
                listResponse);
    }

    @Override
    public RestResponse<QuestionTypeResponse> getOneQuestionType(UUID id) throws DataNotFoundException {
        QuestionType questionType = questionTypeRepo.getQuestionTypeById(id)
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001));
        QuestionTypeResponse questionTypeResponse = QuestionTypeResponse.fromQuestionType(questionType);

        return RestResponse.ok(ResourceBundleConstant.QST_5004, questionTypeResponse);
    }

    @Override
    public RestResponse<QuestionTypeResponse> createQuestionType(QuestionTypeRequest questionTypeRequest) {
        QuestionType questionType = QuestionType.builder()
                .name(questionTypeRequest.getName())
                .build();

        questionTypeRepo.save(questionType);
        QuestionTypeResponse response = QuestionTypeResponse.fromQuestionType(questionType);
        return RestResponse.created(ResourceBundleConstant.QST_5002, response);
    }

    @Override
    public RestResponse<QuestionTypeResponse> updateQuestionType(UUID id, QuestionTypeRequest questionTypeRequest) throws DataNotFoundException {
        QuestionType existingQuestionType =
                questionTypeRepo.getQuestionTypeById(id)
                        .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001));
        existingQuestionType.setName(questionTypeRequest.getName());

        QuestionType questionType = questionTypeRepo.save(existingQuestionType);

        QuestionTypeResponse response = QuestionTypeResponse.fromQuestionType(questionType);
        return RestResponse.accepted(ResourceBundleConstant.QST_5008, response);
    }

    @Override
    public void deleteQuestionType(UUID id) throws DataNotFoundException {
        QuestionType existingQuestionType = questionTypeRepo.getQuestionTypeById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001));
        questionTypeRepo.delete(existingQuestionType);
    }
}
