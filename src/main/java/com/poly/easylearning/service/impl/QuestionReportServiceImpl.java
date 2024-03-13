package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.repo.IQuestionReportRepo;
import com.poly.easylearning.repo.IQuestionTypeRepo;
import com.poly.easylearning.service.IAnswerReportService;
import com.poly.easylearning.service.IQuestionReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class QuestionReportServiceImpl implements IQuestionReportService {
    private final IQuestionReportRepo questionReportRepo;
    private final IAnswerReportService answerReportService;
    private final IQuestionTypeRepo questionTypeRepo;

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
    public List<QuestionReport> createListQuestionReport(List<QuestionReport> questionReports) {
        List<QuestionReport> questionRequests = new ArrayList<>();

        for (QuestionReport questionReport : questionReports) {
            QuestionReport question = QuestionReport.builder()
                    .title(questionReport.getTitle())
                    .description(questionReport.getDescription())
                    .weighted(questionReport.getWeighted())
                    .questionTypeCode(questionReport.getQuestionTypeCode())
                    .build();
            QuestionReport response = saveQuestionReport(question, questionReport.getAnswerReports());
            questionRequests.add(response);
        }

        return questionRequests;
    }

    public QuestionReport saveQuestionReport(QuestionReport questionReport, List<AnswerReport> answerReports) {
        QuestionReport question = questionReportRepo.save(questionReport);
        List<AnswerReport> answerResponses = answerReportService.createAllAnswerReport(answerReports, question.getId());
        return question;
    }
}
