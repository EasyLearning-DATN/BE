package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.ReportItemRequest;
import com.poly.easylearning.repo.IQuestionRepo;
import com.poly.easylearning.repo.IQuestionReportRepo;
import com.poly.easylearning.repo.IQuestionTypeRepo;
import com.poly.easylearning.service.IAnswerReportService;
import com.poly.easylearning.service.IQuestionReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class QuestionReportServiceImpl implements IQuestionReportService {
    private final IQuestionReportRepo questionReportRepo;
    private final IAnswerReportService answerReportService;
    private final IQuestionRepo questionRepo;

    @Override
    public List<QuestionReport> getListQuestionReport() {
        return questionReportRepo.findAll();
    }

    @Override
    public QuestionReport getOneQuestionReport(UUID id) throws DataNotFoundException {
        return questionReportRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUR_7001));
    }

    @Override
    public void createListQuestionReport(List<QuestionReport> questionReports, TestReport testReport) {
        for (QuestionReport questionReport : questionReports) {
            QuestionReport questionCreate = QuestionReport.builder()
                    .title(questionReport.getTitle())
                    .description(questionReport.getDescription())
                    .weighted(questionReport.getWeighted())
                    .questionTypeCode(questionReport.getQuestionTypeCode())
                    .answerOfUser(questionReport.getAnswerOfUser())
                    .testReport(testReport)
                    .build();
            QuestionReport questionReportResponse = questionReportRepo.save(questionCreate);
            answerReportService.createAllAnswerReport(questionReport.getAnswerReports(), questionReportResponse.getId());
        }
    }

    @Override
    public void createListQuestionReportAndAnswerReport(List<ReportItemRequest> reportItemRequests, TestReport testReport) {
        List<QuestionReport> questionReports = reportItemRequests.stream().map(reportItemRequest -> {
            Question question = questionRepo.findById(reportItemRequest.getQuestionId()).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUE_7001));
            List<AnswerReport> answerReports = question.getAnswers().stream().map(answer -> AnswerReport.builder()
                    .value(answer.getValue())
                    .isCorrect(answer.getIsCorrect())
                    .build()
            ).collect(Collectors.toList());
            return QuestionReport.builder()
                    .testReport(testReport)
                    .title(question.getTitle())
                    .description(question.getDescription())
                    .questionTypeCode(question.getQuestionType().getCode())
                    .weighted(question.getWeighted())
                    .answerReports(answerReports)
                    .answerOfUser(reportItemRequest.getAnswers().toString())
                    .build();
        }).collect(Collectors.toList());

        createListQuestionReport(questionReports, testReport);
    }
}
