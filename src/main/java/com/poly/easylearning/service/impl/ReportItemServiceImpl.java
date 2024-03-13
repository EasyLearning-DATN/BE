package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.QuestionTestResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.*;
import com.poly.easylearning.service.IQuestionReportService;
import com.poly.easylearning.service.IQuestionTestService;
import com.poly.easylearning.service.IReportItemService;
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
public class ReportItemServiceImpl implements IReportItemService {
    private final ITestReportRepo testReportRepo;
    private final IQuestionTestRepo questionTestRepo;
    private final IReportItemRepo reportItemRepo;
    private final IQuestionReportService questionReportService;

    @Override
    public List<ReportItem> createListReportItem(Set<UUID> questionTestIds, UUID testReportId, List<String> answerOfUser) {
        TestReport testReport = testReportRepo.findById(testReportId).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.TER_10001));
        List<ReportItem> reportItems = questionTestIds.stream().map(uuid -> {
            QuestionTest questionTest = questionTestRepo.findById(uuid).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUT_12001));
            return ReportItem.builder()
                    .testReport(testReport)
                    .questionTest(questionTest)
                    .answerOfUser(answerOfUser.toString())
                    .build();
        }).collect(Collectors.toList());
        List<ReportItem> reportItemList = reportItemRepo.saveAll(reportItems);
        return reportItemList;
    }
}
