package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.LessonRequest;
import com.poly.easylearning.payload.response.GetListLessonResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.ILessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_LESSON)
public class LessonMemberController {
    private final ILessonService lessonService;

    @PostMapping("")
    public ResponseEntity<RestResponse<GetListLessonResponse>> createLesson(
            @Valid @RequestBody LessonRequest lessonRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(lessonService.createLesson(lessonRequest));
    }

    @PutMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<GetListLessonResponse>> updateLesson(@PathVariable(name = "id") UUID id,
                                                                            @Valid @RequestBody LessonRequest lessonRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(lessonService.updateLesson(id, lessonRequest));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<Void> deleteLesson(@PathVariable(name = "id") UUID id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
