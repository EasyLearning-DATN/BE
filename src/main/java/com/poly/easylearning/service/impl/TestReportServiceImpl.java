package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.TestReportRequest;
import com.poly.easylearning.payload.request.TestRequest;
import com.poly.easylearning.payload.response.*;
import com.poly.easylearning.repo.*;
import com.poly.easylearning.service.IQuestionTestService;
import com.poly.easylearning.service.IReportItemService;
import com.poly.easylearning.service.ITestReportService;
import com.poly.easylearning.utils.DateUtil;
import com.poly.easylearning.utils.SecurityContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class TestReportServiceImpl implements ITestReportService {
    private final ITestReportRepo testReportRepo;
    private final ITestRepo testRepo;
    private final IUserInfoRepo userInfoRepo;
    private final IReportItemService reportItemService;

    @Override
    public RestResponse<ListResponse<TestReportResponse>> getListTestReport(Integer userId, String dateStart, String dateEnd, String createdBy, PageRequest pageRequest) {
        UUID createdByMapper = null;
        if (!createdBy.isEmpty()) {
            try {
                createdByMapper = UUID.fromString(createdBy);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.DAT_8002);
            }
        }


        Page<TestReport> pageReponse = testReportRepo.searchTestReport(userId, DateUtil.fromString(dateStart), DateUtil.fromString(dateEnd), createdByMapper, pageRequest);
        List<TestReportResponse> testReportResponses = pageReponse.get().map(TestReportResponse::fromTestReport).toList();
        ListResponse<TestReportResponse> listResponse = ListResponse.build(pageReponse.getTotalPages(), testReportResponses);
        return RestResponse.ok(ResourceBundleConstant.TER_10003, listResponse);
    }

    @Override
    public RestResponse<TestReportResponse> getOneTestReport(UUID id) throws DataNotFoundException {
        TestReport testReport = testReportRepo.getTestReportById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TER_10001));
        TestReportResponse testReportResponse = TestReportResponse.fromTestReport(testReport);

        return RestResponse.ok(ResourceBundleConstant.TER_10004, testReportResponse);
    }

    @Override
    public RestResponse<TestReportResponse> createTestReport(TestReportRequest testReportRequest) {
        Test existingTest = testRepo.findById(testReportRequest.getTestId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TES_10001));
        UserInfo existingUserInfo = userInfoRepo.findById(testReportRequest.getUserInfoId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.USR_2002));

        TestReport testReport = TestReport.builder()
                .test(existingTest)
                .userInfo(existingUserInfo)
                .doingDate(LocalDateTime.now())
                .build();

        TestReport testReportReport = testReportRepo.save(testReport);



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
