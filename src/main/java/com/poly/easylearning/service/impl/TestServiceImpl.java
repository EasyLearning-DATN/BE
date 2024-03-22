package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.Image;
import com.poly.easylearning.entity.Test;
import com.poly.easylearning.entity.ViewResultType;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.QuestionTestRequest;
import com.poly.easylearning.payload.request.TestRequest;
import com.poly.easylearning.payload.response.*;
import com.poly.easylearning.repo.IImageRepo;
import com.poly.easylearning.repo.ITestRepo;
import com.poly.easylearning.repo.IViewResultTypeRepo;
import com.poly.easylearning.service.IQuestionTestService;
import com.poly.easylearning.service.ITestService;
import com.poly.easylearning.utils.DateUtil;
import com.poly.easylearning.utils.SecurityContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class TestServiceImpl implements ITestService {
    private final ITestRepo testRepo;
    private final IImageRepo imageRepo;
    private final IViewResultTypeRepo viewResultTypeRepo;
    private final IQuestionTestService questionTestService;

    @Override
    public RestResponse<ListResponse<TestResponse>> getListTest(String keyword, String id, String dateStart, String dateEnd, String createdBy, PageRequest pageRequest) {
        UUID idMapper = null;
        UUID createdByMapper = null;
        if (!id.isEmpty()) {
            try {
                idMapper = UUID.fromString(id);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.DAT_8002);
            }
        }
        if (!createdBy.isEmpty()) {
            try {
                createdByMapper = UUID.fromString(createdBy);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.DAT_8002);
            }
        }


        Page<Test> pageReponse = testRepo.searchTest(keyword, idMapper, DateUtil.fromString(dateStart), DateUtil.fromString(dateEnd), createdByMapper, pageRequest);
        List<TestResponse> testResponses = pageReponse.get().map(TestResponse::fromTest).toList();
        ListResponse<TestResponse> listResponse = ListResponse.build(pageReponse.getTotalPages(), testResponses);
        return RestResponse.ok(ResourceBundleConstant.TES_10003, listResponse);
    }

    @Override
    public RestResponse<TestResponse> getOneTest(UUID id) throws DataNotFoundException {
        Test test = testRepo.getTestById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TES_10001));
        TestResponse testResponse = TestResponse.fromTest(test);

        return RestResponse.ok(ResourceBundleConstant.TES_10004, testResponse);
    }

    @Override
    public RestResponse<TestResponse> createTest(TestRequest testRequest) {
        Image image = imageRepo.findByPublicId(testRequest.getImageId()).orElse(imageRepo.findByPublicId(DefaultValueConstants.IMAGE_LESSON_DEFAULT).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.IMG_3005)));

        ViewResultType viewResultType = viewResultTypeRepo.findViewResultTypeByCode(testRequest.getViewResultTypeCode()).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.VRT_11001));

        Test newTest = Test.builder()
                .name(testRequest.getName())
                .description(testRequest.getDescription())
                .timeTotal(testRequest.getTimeTotal())
                .timeQuestion(testRequest.getTimeQuestion())
                .totalQuestion(testRequest.getQuestionIds().size()).image(image)
                .viewResultType(viewResultType)
                .userInfo(SecurityContextUtils.getCurrentUser().getUserInfo())
                .doingTime(0)
                .build();
        Test test = testRepo.save(newTest);
        questionTestService.createListQuestionTest(testRequest.getQuestionIds(), test.getId());
        return RestResponse.created(ResourceBundleConstant.TES_10002, TestResponse.fromTest(test));
    }

    @Override
    public RestResponse<TestResponse> updateTest(UUID id, TestRequest testRequest) throws DataNotFoundException {
        Test existingTest = testRepo.findById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TES_10001));
        ViewResultType viewResultType = viewResultTypeRepo.findViewResultTypeByCode(testRequest.getViewResultTypeCode()).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.VRT_11001));
        Image image = imageRepo.findByPublicId(testRequest.getImageId()).orElse(imageRepo.findByPublicId(DefaultValueConstants.IMAGE_LESSON_DEFAULT).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.IMG_3005)));
        existingTest.setName(testRequest.getName());
        existingTest.setDescription(testRequest.getDescription());
        existingTest.setTimeTotal(testRequest.getTimeTotal());
        existingTest.setTimeQuestion(testRequest.getTimeQuestion());
        existingTest.setTotalQuestion(testRequest.getQuestionIds().size());
        existingTest.setViewResultType(viewResultType);
        existingTest.setImage(image);
        Test test = testRepo.save(existingTest);
        List<QuestionTestResponse> questionTestResponses = new ArrayList<>();
        if (!testRequest.getQuestionIds().isEmpty()) {
             questionTestResponses = questionTestService.updateListQuestionTest(testRequest.getQuestionIds(), id).data();
        }

        TestResponse testResponse = TestResponse.fromTest(test);
        testResponse.setQuestions(questionTestResponses.stream().map(QuestionTestResponse::getQuestionResponse).collect(Collectors.toSet()));

        return RestResponse.created(ResourceBundleConstant.TES_10002, testResponse);
    }

    @Override
    public void deleteTest(UUID id) throws DataNotFoundException {
        Test existingTest = testRepo.findById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TES_10001));
        existingTest.setIsDeleted(true);
        testRepo.save(existingTest);
    }
}
