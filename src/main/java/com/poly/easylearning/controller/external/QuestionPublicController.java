package com.poly.easylearning.controller.external;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.QuestionResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + SystemConstant.API_QUESTION)
public class QuestionPublicController {
    private final IQuestionService questionService;

    @GetMapping("")
    public ResponseEntity<RestResponse<ListResponse<QuestionResponse>>> getListQuestion(
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "id" , defaultValue = "") String id,
            @RequestParam(value = "dateStart", defaultValue = "") String dateStart,
            @RequestParam(value = "dateEnd", defaultValue = "") String dateEnd,
            @RequestParam(value = "createdBy", defaultValue = "") String createdBy,
            @RequestParam(value = "lessonId", defaultValue = "") String lessonId
    ) {

        //Format sắp xếp
        Sort.Direction direction = Sort.Direction.DESC;
        if (sort.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        PageRequest pageRequest = PageRequest.of(page, limit, direction, sortBy);
        return ResponseEntity.ok(questionService.getListQuestion(key, id, dateStart, dateEnd, createdBy, lessonId, pageRequest));
    }

    @GetMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<QuestionResponse>> getOneQuestion(
            @PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(questionService.getOneQuestion(id));
    }
}
