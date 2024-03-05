package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.Answer;
import com.poly.easylearning.entity.QuestionType;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.AnswerRequest;
import com.poly.easylearning.payload.response.AnswerResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.QuestionTypeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IAnswerRepo;
import com.poly.easylearning.service.IAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class AnswerServiceImpl implements IAnswerService {
    private final IAnswerRepo answerRepo;

    @Override
    public RestResponse<ListResponse<AnswerResponse>> getListAnswer(String keyword, PageRequest pageRequest) {
        Page<Answer> pageReponse = answerRepo.searchAnswer(keyword, pageRequest);
        List<AnswerResponse> answerResponses = pageReponse.get().map(AnswerResponse::fromAnswer).toList();
        ListResponse<AnswerResponse> listResponse = ListResponse.build(pageReponse.getTotalPages(), answerResponses);
        return RestResponse.ok(ResourceBundleConstant.ANS_6003,
                listResponse);
    }

    @Override
    public RestResponse<AnswerResponse> getOneAnswer(UUID id) throws DataNotFoundException {
        Answer answer = answerRepo.getAnswerById(id)
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.ANS_6001));
        AnswerResponse answerResponse = AnswerResponse.fromAnswer(answer);

        return RestResponse.ok(ResourceBundleConstant.ANS_6004, answerResponse);
    }

    @Override
    public RestResponse<AnswerResponse> createAnswer(AnswerRequest answerRequest) {
        Answer answer = Answer.builder()
                .value(answerRequest.getValue())
                .isCorrect(answerRequest.getIsCorrect())
                .build();

        answerRepo.save(answer);
        AnswerResponse response = AnswerResponse.fromAnswer(answer);
        return RestResponse.created(ResourceBundleConstant.ANS_6002, response);
    }

    @Override
    public RestResponse<AnswerResponse> updateAnswer(UUID id, AnswerRequest answerRequest) throws DataNotFoundException {
        Answer existingAnswer =
                answerRepo.getAnswerById(id)
                        .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.ANS_6001));
        existingAnswer.setValue(answerRequest.getValue());
        existingAnswer.setIsCorrect(answerRequest.getIsCorrect());

        Answer answer = answerRepo.save(existingAnswer);

        AnswerResponse response = AnswerResponse.fromAnswer(answer);
        return RestResponse.accepted(ResourceBundleConstant.ANS_6008, response);
    }

    @Override
    public void deleteOneAnswer(UUID id) throws DataNotFoundException {
        Answer existingAnswer = answerRepo.getAnswerById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.ANS_6001));
        answerRepo.delete(existingAnswer);
    }

    @Override
    public void deleteListAnswer(List<UUID> ids) throws DataNotFoundException {
//        Answer existingAnswer = answerRepo.getAnswerById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.ANS_6001));
        answerRepo.deleteAllByIdIn(ids);
    }
}
