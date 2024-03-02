
package com.poly.easylearning.controller.external;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.QuestionTypeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IQuestionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + SystemConstant.API_QUESTION_TYPE)
public class QuestionTypePublicController {
    private final IQuestionTypeService questionTypeService;

    @GetMapping("")
    public ResponseEntity<RestResponse<ListResponse<QuestionTypeResponse>>> getListQuestionType(
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        //Format sắp xếp
        Sort.Direction direction = Sort.Direction.DESC;
        if (sort.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        PageRequest pageRequest = PageRequest.of(page, limit, direction, sortBy);
        return ResponseEntity.ok(questionTypeService.getListQuestionType(key, pageRequest));
    }

    @GetMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<QuestionTypeResponse>> getOneQuestionType(
            @PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(questionTypeService.getOneQuestionType(id));
    }
}
