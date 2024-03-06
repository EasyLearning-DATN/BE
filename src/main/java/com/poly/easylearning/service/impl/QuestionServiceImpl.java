package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.QuestionRequest;
import com.poly.easylearning.payload.response.*;
import com.poly.easylearning.repo.*;
import com.poly.easylearning.service.IAnswerService;
import com.poly.easylearning.service.IQuestionService;
import com.poly.easylearning.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class QuestionServiceImpl implements IQuestionService {
    private final IQuestionRepo questionRepo;
    private final IAnswerService answerService;
    private final IQuestionTypeRepo questionTypeRepo;
    private final ILessonRepo lessonRepo;

    @Override
    public RestResponse<ListResponse<QuestionResponse>> getListQuestion(String keyword, String id, String dateStart, String dateEnd, String createdBy, String lessonId, PageRequest pageRequest) {
        UUID idMapper = null;
        UUID createdByMapper = null;
        UUID lessonIdMapper = null;
        if (!id.isEmpty()) {
            try {
                idMapper = UUID.fromString(id);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.DAT_8002);
            }
        }
        if (!createdBy.isEmpty()) {
            try {
                createdByMapper = UUID.fromString(createdBy);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.DAT_8002);
            }
        }
        if (!lessonId.isEmpty()) {
            try {
                createdByMapper = UUID.fromString(createdBy);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.DAT_8002);
            }
        }

        Page<Question> pageReponse = questionRepo.searchQuestion(keyword, idMapper, DateUtil.fromString(dateStart), DateUtil.fromString(dateEnd), createdByMapper, lessonIdMapper, pageRequest);
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
        Lesson existingLesson = lessonRepo.findById(questionRequest.getLessonId())
                .orElseThrow(()-> new DataNotFoundException(ResourceBundleConstant.LSN_4001));
        QuestionType existingQuestionType = questionTypeRepo.getQuestionTypeById(questionRequest.getQuestionTypeId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001));

        Question question = Question.builder()
                .title(questionRequest.getTitle())
                .description(questionRequest.getDescription())
                .weighted(questionRequest.getWeighted())
                .lesson(existingLesson)
                .questionType(existingQuestionType)
                .build();
        Question questionResponse = questionRepo.save(question);
        answerService.createAllAnswer(questionRequest.getAnswers(), questionResponse.getId());
        QuestionResponse response = QuestionResponse.fromQuestion(questionRepo.findById(questionResponse.getId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001)));
        return RestResponse.created(ResourceBundleConstant.QUE_7002, response);
    }

    @Override
    public RestResponse<QuestionResponse> updateQuestion(UUID id, QuestionRequest questionRequest) throws DataNotFoundException {
        Question existingQuestion =
                questionRepo.getQuestionById(id)
                        .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QUE_7001));
        QuestionType existingQuestionType = questionTypeRepo.getQuestionTypeById(questionRequest.getQuestionTypeId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001));

        existingQuestion.setTitle(questionRequest.getTitle());
        existingQuestion.setDescription(questionRequest.getDescription());
        existingQuestion.setWeighted(questionRequest.getWeighted());
        existingQuestion.setQuestionType(existingQuestionType);

        Question questionResponse = questionRepo.save(existingQuestion);

        QuestionResponse response = QuestionResponse.fromQuestion(questionRepo.findById(questionResponse.getId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001)));
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
