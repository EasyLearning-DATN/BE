package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.ReportItemRequest;
import com.poly.easylearning.repo.IQuestionRepo;
import com.poly.easylearning.repo.IQuestionReportRepo;
import com.poly.easylearning.repo.ITestReportRepo;
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
    private final ITestReportRepo testReportRepo;
    private static String SINGEL_CORRECT_ANSWER = "sca";
    private static String MULTILPLE_CORRECT_ANSWER = "mca";
    private static String FILL_IN_THE_BLANK = "fitb";
    private static int TOTAL_POINT_TEST = 10;

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
                    .point(questionReport.getPoint())
                    .build();
            QuestionReport questionReportResponse = questionReportRepo.save(questionCreate);
            answerReportService.createAllAnswerReport(questionReport.getAnswerReports(), questionReportResponse.getId());
        }

        testReportRepo.save(testReport);
    }

    @Override
    public void createListQuestionReportAndAnswerReport(List<ReportItemRequest> reportItemRequests, TestReport testReport) {
        Test test = testReport.getTest();
        double totalWeight = test.getQuestionTests().stream()
                .map(questionTest -> questionTest.getQuestion().getWeighted())
                .reduce(0.0, Double::sum);
        double pointEachWeight = TOTAL_POINT_TEST / totalWeight;
        List<QuestionReport> questionReports = reportItemRequests.stream()
                .map(reportItemRequest ->
                {
                    Question question = questionRepo.findById(reportItemRequest.getQuestionId()).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUE_7001));
                    List<AnswerReport> answerReports = question.getAnswers().stream().map(answer -> AnswerReport.builder()
                            .value(answer.getValue())
                            .isCorrect(answer.getIsCorrect())
                            .build()
                    ).collect(Collectors.toList());
                    double point = calculatePoint(question, reportItemRequest.getAnswers(), pointEachWeight);
                    return QuestionReport.builder()
                            .testReport(testReport)
                            .title(question.getTitle())
                            .description(question.getDescription())
                            .questionTypeCode(question.getQuestionType().getCode())
                            .weighted(question.getWeighted())
                            .answerReports(answerReports)
                            .point(point)
                            .answerOfUser(String.join(DefaultValueConstants.JOIN_CHARACTER, reportItemRequest.getAnswers()))
                            .build();
                }).collect(Collectors.toList());
        double totalPoint = questionReports.stream().map(QuestionReport::getPoint).reduce(0.0, Double::sum);
        testReport.setTotalPoint(totalPoint);
        createListQuestionReport(questionReports, testReport);
    }

    private double calculatePoint(Question question, List<String> answerOfUsers, double point) {
        String code = question.getQuestionType().getCode();
        if (SINGEL_CORRECT_ANSWER.equals(code)) {
            return calculatePointScaAndFitbQuestion(question, answerOfUsers, point);
        } else if (MULTILPLE_CORRECT_ANSWER.equals(code)) {
            return calculatePointMCAQuestion(question, answerOfUsers, point);
        } else if (FILL_IN_THE_BLANK.equals(code)) {
            return calculatePointScaAndFitbQuestion(question, answerOfUsers, point);
        }

        return 0;
    }

    private double calculatePointScaAndFitbQuestion(Question question, List<String> answerOfUsers, double point) {
        List<String> answers = question.getAnswers().stream().filter(Answer::getIsCorrect).map(Answer::getValue).toList();
        double weight = question.getWeighted();
        if (!answerOfUsers.isEmpty()) {
            String answerOfUser = answerOfUsers.get(0);
            for (String answerCorect : answerOfUsers) {
                if (answerCorect.equals(answerOfUser)) {
                    return (double) Math.round(weight * point * 100) / 100;
                }
            }
        }
        return 0;
    }

    private double calculatePointMCAQuestion(Question question, List<String> answerOfUsers, double point) {
        List<Answer> answers = question.getAnswers();
        List<String> answersValue = answers.stream().filter(Answer::getIsCorrect).map(Answer::getValue).toList();
        int answerCorrectAmount = answers.stream().filter(Answer::getIsCorrect).toList().size();
        double weight = point * question.getWeighted();
        double pointCorrect = point * weight / answerCorrectAmount;
        int count = 0;
        for (String answerOfUserLoop : answerOfUsers) {
            for (String answerCorect : answersValue) {
                if (answerCorect.equals(answerOfUserLoop)) {
                    count++;
                }
            }
        }
        return (double) Math.round(count * pointCorrect * 100) / 100;
    }
}
