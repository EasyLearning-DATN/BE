package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.Question;
import com.poly.easylearning.entity.QuestionType;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.QuestionRequest;
import com.poly.easylearning.payload.request.QuestionTypeRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.QuestionResponse;
import com.poly.easylearning.payload.response.QuestionTypeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IQuestionRepo;
import com.poly.easylearning.repo.IQuestionTypeRepo;
import com.poly.easylearning.service.IQuestionService;
import com.poly.easylearning.service.IQuestionTypeService;
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
public class QuestionServiceImpl implements IQuestionService {
    private final IQuestionRepo questionRepo;

    @Override
    public RestResponse<ListResponse<QuestionResponse>> getListQuestion(String keyword, PageRequest pageRequest) {
        Page<Question> pageReponse = questionRepo.searchQuestion(keyword, pageRequest);
        List<QuestionResponse> questionResponses = pageReponse.get().map(QuestionResponse::fromQuestion).toList();
        ListResponse<QuestionResponse> listResponse = ListResponse.build(pageReponse.getTotalPages(), questionResponses);
        return RestResponse.ok(ResourceBundleConstant.QUE_7003,
                listResponse);
    }

    @Override
    public RestResponse<QuestionResponse> getOneQuestion(UUID id) throws DataNotFoundException {
        Question question = questionRepo.getQuestionById(id)
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUE_7001));
        QuestionResponse questionResponse = QuestionResponse.fromQuestion(question);

        return RestResponse.ok(ResourceBundleConstant.QUE_7004, questionResponse);
    }

    @Override
    public RestResponse<QuestionResponse> createQuestion(QuestionRequest questionRequest) {
        Question question = Question.builder()
                .title(questionRequest.getTitle())
                .description(questionRequest.getDescription())
                .imagePath(questionRequest.getImagePath())
                .weighted(questionRequest.getWeighted())
                .build();

        questionRepo.save(question);
        QuestionResponse response = QuestionResponse.fromQuestion(question);
        return RestResponse.created(ResourceBundleConstant.QUE_7002, response);
    }

    @Override
    public RestResponse<QuestionResponse> updateQuestion(UUID id, QuestionRequest questionRequest) throws DataNotFoundException {
        Question existingQuestion =
                questionRepo.getQuestionById(id)
                        .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUE_7001));
        existingQuestion.setTitle(questionRequest.getTitle());
        existingQuestion.setDescription(questionRequest.getDescription());
        existingQuestion.setImagePath(questionRequest.getImagePath());
        existingQuestion.setWeighted(questionRequest.getWeighted());

        Question question = questionRepo.save(existingQuestion);

        QuestionResponse response = QuestionResponse.fromQuestion(question);
        return RestResponse.accepted(ResourceBundleConstant.QUE_7008, response);
    }

    @Override
    public void deleteQuestion(UUID id) throws DataNotFoundException {
        //Xóa mềm
        Question existingQuestion = questionRepo.getQuestionById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUE_7001));
        existingQuestion.setIsDeleted(true);
        questionRepo.save(existingQuestion);
    }
}
