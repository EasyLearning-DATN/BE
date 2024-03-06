package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.Answer;
import com.poly.easylearning.entity.Question;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.AnswerRequest;
import com.poly.easylearning.payload.response.AnswerResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IAnswerRepo;
import com.poly.easylearning.repo.IQuestionRepo;
import com.poly.easylearning.service.IAnswerService;
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
public class AnswerServiceImpl implements IAnswerService {
    private final IAnswerRepo answerRepo;
    private final IQuestionRepo questionRepo;
    @Override
    public RestResponse<List<AnswerResponse>> createAllAnswer(List<AnswerRequest> answerRequests, UUID questionId) {
        Question existingQuestion = questionRepo.getQuestionById(questionId)
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001));
        List<Answer> answers = new ArrayList<>();
        List<AnswerResponse> answerResponses = new ArrayList<>();

        answerRepo.deleteAnswersByQuestionId(existingQuestion.getId());

        for(AnswerRequest answerRequest : answerRequests){
            Answer answer = Answer.builder()
                    .value(answerRequest.getValue())
                    .isCorrect(answerRequest.getIsCorrect())
                    .question(existingQuestion)
                    .build();
            answers.add(answer);
        }

        List<Answer> answersAfter = answerRepo.saveAll(answers);

        for(Answer answer : answersAfter){
            AnswerResponse response = AnswerResponse.fromAnswer(answer);
            answerResponses.add(response);
        }

        return RestResponse.created(ResourceBundleConstant.ANS_6002, answerResponses);
    }
}
