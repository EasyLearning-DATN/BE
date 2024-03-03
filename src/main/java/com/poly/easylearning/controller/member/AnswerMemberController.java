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

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_ANSWER)
public class AnswerMemberController {
    private final IAnswerService answerService;

    @PostMapping("")
    public ResponseEntity<RestResponse<AnswerResponse>> createAnswer(
            @Valid @RequestBody AnswerRequest answerRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(answerService.createAnswer(answerRequest));
    }

    @PutMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<AnswerResponse>> updateAnswer(@PathVariable(name = "id") UUID id,
                                                                     @Valid @RequestBody AnswerRequest answerRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(answerService.updateAnswer(id, answerRequest));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<Void> deleteAnswer(@PathVariable(name = "id") UUID id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
