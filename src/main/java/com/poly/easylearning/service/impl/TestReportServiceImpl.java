package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.TestReportRequest;
import com.poly.easylearning.payload.response.*;
import com.poly.easylearning.repo.*;
import com.poly.easylearning.service.IQuestionReportService;
import com.poly.easylearning.service.ITestReportService;
import com.poly.easylearning.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class TestReportServiceImpl implements ITestReportService {
    private final ITestReportRepo testReportRepo;
    private final ITestRepo testRepo;
    private final IUserInfoRepo userInfoRepo;
    private final IQuestionReportService questionReportService;

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
        TestReport testReport = saveTestReport(testReportRequest);
        return RestResponse.created(ResourceBundleConstant.TER_10002, TestReportResponse.fromTestReport(testReport));
    }

    @Override
    public void deleteTestReport(UUID id) throws DataNotFoundException {
        TestReport existingTestReport = testReportRepo.findById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TER_10001));
        existingTestReport.setIsDeleted(true);
        testReportRepo.save(existingTestReport);
    }

    public TestReport saveTestReport(TestReportRequest testReportRequest) {
        Test existingTest = testRepo.findById(testReportRequest.getTestId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TES_10001));
        UserInfo existingUserInfo = userInfoRepo.findById(testReportRequest.getUserInfoId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.USR_2002));

        TestReport testReport = TestReport.builder()
                .test(existingTest)
                .userInfo(existingUserInfo)
                .doingDate(LocalDateTime.now())
                .build();
        TestReport testReportResponse = testReportRepo.save(testReport);
        CompletableFuture<Void> listQuestionReportFuture = CompletableFuture.runAsync(() ->
                {
                    questionReportService.createListQuestionReportAndAnswerReport(testReportRequest.getReportItems(), testReportResponse);
                });
        return testReportResponse;
    }
}
