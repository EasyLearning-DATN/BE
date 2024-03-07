package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.QuestionRequest;
import com.poly.easylearning.payload.response.QuestionResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_QUESTION)
public class QuestionMemberController {
    private final IQuestionService questionService;

    @PostMapping("")
    public ResponseEntity<RestResponse<QuestionResponse>> createQuestion(
            @Valid @RequestBody QuestionRequest questionRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(questionService.createQuestion(questionRequest));
    }

    @PostMapping(SystemConstant.API_CREATE_LIST)
    public ResponseEntity<RestResponse<List<QuestionResponse>>> createListQuestion(
            @Valid @RequestBody List<QuestionRequest> questionRequests, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(questionService.createListQuestion(questionRequests));
    }

    @PutMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<QuestionResponse>> updateQuestion(@PathVariable(name = "id") UUID id,
                                                                     @Valid @RequestBody QuestionRequest questionRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(questionService.updateQuestion(id, questionRequest));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<Void> deleteQuestion(@PathVariable(name = "id") UUID id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
