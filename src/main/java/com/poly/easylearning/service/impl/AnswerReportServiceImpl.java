package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.AnswerReport;
import com.poly.easylearning.entity.QuestionReport;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.repo.IAnswerReportRepo;
import com.poly.easylearning.repo.IQuestionReportRepo;
import com.poly.easylearning.service.IAnswerReportService;
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
public class AnswerReportServiceImpl implements IAnswerReportService {
    private final IAnswerReportRepo answerReportRepo;
    private final IQuestionReportRepo questionReportRepo;

    @Override
    public List<AnswerReport> createAllAnswerReport(List<AnswerReport> answerReports, UUID questionReportId) {
        QuestionReport existingQuestionReport = questionReportRepo.findById(questionReportId)
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUR_7001));
        List<AnswerReport> answerReportList = new ArrayList<>();

        for (AnswerReport answerReport : answerReports) {
            AnswerReport answer = AnswerReport.builder()
                    .value(answerReport.getValue())
                    .isCorrect(answerReport.getIsCorrect())
                    .questionReport(existingQuestionReport)
                    .build();
            answerReportList.add(answer);
        }

        List<AnswerReport> answersReportAfter = answerReportRepo.saveAll(answerReportList);

        return answersReportAfter;
    }
}
