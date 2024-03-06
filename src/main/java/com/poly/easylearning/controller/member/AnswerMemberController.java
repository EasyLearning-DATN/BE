package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.AnswerRequest;
import com.poly.easylearning.payload.response.AnswerResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_ANSWER)
public class AnswerMemberController {
    private final IAnswerService answerService;

    @PostMapping("")
    public ResponseEntity<RestResponse<List<AnswerResponse>>> createAnswer(
            @Valid @RequestBody List<AnswerRequest> answerRequests, BindingResult result, @RequestParam("question_id") UUID questionId) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(answerService.createAllAnswer(answerRequests, questionId));
    }
}
