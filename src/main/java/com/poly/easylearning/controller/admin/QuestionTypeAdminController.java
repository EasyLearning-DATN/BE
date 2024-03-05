package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.QuestionTypeRequest;
import com.poly.easylearning.payload.response.QuestionTypeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IQuestionTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_QUESTION_TYPE)
public class QuestionTypeAdminController {
    private final IQuestionTypeService questionTypeService;

    @PostMapping("")
    public ResponseEntity<RestResponse<QuestionTypeResponse>> createQuestionType(
            @Valid @RequestBody QuestionTypeRequest questionTypeRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(questionTypeService.createQuestionType(questionTypeRequest));
    }

    @PutMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<QuestionTypeResponse>> updateQuestionType(@PathVariable(name = "id") UUID id,
                                                                     @Valid @RequestBody QuestionTypeRequest questionTypeRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(questionTypeService.updateQuestionType(id, questionTypeRequest));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<Void> deleteQuestionType(@PathVariable(name = "id") UUID id) {
        questionTypeService.deleteQuestionType(id);
        return ResponseEntity.noContent().build();
    }
}
