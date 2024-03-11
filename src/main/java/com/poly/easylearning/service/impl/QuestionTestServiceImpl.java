package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.Question;
import com.poly.easylearning.entity.QuestionTest;
import com.poly.easylearning.entity.Test;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.response.*;
import com.poly.easylearning.repo.IQuestionRepo;
import com.poly.easylearning.repo.IQuestionTestRepo;
import com.poly.easylearning.repo.ITestRepo;
import com.poly.easylearning.service.IQuestionTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class QuestionTestServiceImpl implements IQuestionTestService {
    private final IQuestionTestRepo questionTestRepo;
    private final IQuestionRepo questionRepo;
    private final ITestRepo testRepo;

    @Override
    public RestResponse<ListResponse<QuestionTestResponse>> getListQuestionTest(PageRequest pageRequest) {
        Page<QuestionTest> pageResponse = questionTestRepo.findAll(pageRequest);
        List<QuestionTestResponse> listResponse = pageResponse.stream().map(QuestionTestResponse::fromQuestionTest).toList();
        ListResponse<QuestionTestResponse> questionTestResponses = ListResponse.build(pageResponse.getTotalPages(), listResponse);
        return RestResponse.ok(ResourceBundleConstant.QUT_12003, questionTestResponses);
    }

    @Override
    public RestResponse<QuestionTestResponse> getOneQuestionTest(UUID id) throws DataNotFoundException {
        QuestionTest questionTest = questionTestRepo.findById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUT_12001));
        return RestResponse.ok(ResourceBundleConstant.QUT_12002, QuestionTestResponse.fromQuestionTest(questionTest));
    }

    @Override
    public RestResponse<List<QuestionTestResponse>> createListQuestionTest(Set<UUID> questionIds, UUID testId) {
        Test test = testRepo.findById(testId).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TES_10001));
        List<QuestionTest> questionTests = questionIds.stream().map(questionTestRequest -> {
            Question question = questionRepo.findById(questionTestRequest)
                    .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUE_7001));
            return QuestionTest.builder()
                    .question(question)
                    .test(test)
                    .build();
        }).collect(Collectors.toList());
        List<QuestionTest> questionTestList = questionTestRepo.saveAll(questionTests);
        List<QuestionTestResponse> response = questionTestList.stream()
                .map(QuestionTestResponse::fromQuestionTest).toList();
        return RestResponse.created(ResourceBundleConstant.QUT_12002, response);
    }

    @Override
    public RestResponse<List<QuestionTestResponse>> updateListQuestionTest(Set<UUID> questionIdRequests, UUID testId) throws DataNotFoundException {
        Test test = testRepo.findById(testId).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TES_10001));
        List<UUID> questionIds = test.getQuestionTests().stream().map(questionTest -> questionTest.getQuestion().getId()).toList();
        Set<UUID> deleteIds = new HashSet<>();
        Set<UUID> createIds = new HashSet<>();
        RestResponse<List<QuestionTestResponse>> response;

        for (UUID existingId : questionIds) {
            if(!questionIdRequests.contains(existingId)){
                deleteIds.add(existingId);
            }
        }

        for (UUID id : questionIdRequests) {
            if(!questionIds.contains(id)){
                createIds.add(id);
            }
        }

        if(!deleteIds.isEmpty()){
            deleteListQuestionTest(deleteIds, testId);
        }

        if(!createIds.isEmpty()){
            response = createListQuestionTest(createIds, testId);
            return RestResponse.created(ResourceBundleConstant.QUT_12002, response.data());
        }

        return RestResponse.created(ResourceBundleConstant.QUT_12002, new ArrayList<QuestionTestResponse>());
    }

    @Override
    public void deleteListQuestionTest(Set<UUID> questionIdRequests, UUID testId) throws DataNotFoundException {
        questionTestRepo.deleteAllByQuestionAndTest(questionIdRequests, testId);
    }
}
