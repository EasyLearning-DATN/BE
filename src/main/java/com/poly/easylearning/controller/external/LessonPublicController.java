package com.poly.easylearning.controller.external;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.payload.request.LessonRequest;
import com.poly.easylearning.payload.response.LessonResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.ILessonRepo;
import com.poly.easylearning.service.ILessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + SystemConstant.API_LESSON)
public class LessonPublicController {
    private final ILessonService lessonService;
    private final ILessonRepo lessonRepo;

    @GetMapping("")
    public ResponseEntity<RestResponse<ListResponse<LessonResponse>>> getListLesson(
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "dateStart", defaultValue = "") String dateStart,
            @RequestParam(value = "dateEnd", defaultValue = "") String dateEnd,
            @RequestParam(value = "createdBy", defaultValue = "") String createdBy,
            @RequestParam(value = "isPublic", defaultValue = "") String isPublic
    ) {

        //Format sắp xếp
        Sort.Direction direction = Sort.Direction.DESC;
        if (sort.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        PageRequest pageRequest = PageRequest.of(page, limit, direction, sortBy);
        return ResponseEntity.ok(lessonService.getListLesson(key, id, dateStart, dateEnd, createdBy, isPublic, pageRequest));
    }

    @GetMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<LessonResponse>> getOneLesson(
            @PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(lessonService.getOneLessonIncrementAccess(id));
    }
}
