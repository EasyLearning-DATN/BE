package com.poly.easylearning.controller.external;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.enums.Scope;
import com.poly.easylearning.service.ICommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + SystemConstant.API_COMMENT)
public class CommentPublicController {
    private final ICommentService commentService;
    @GetMapping
    public ResponseEntity<?> findByLesson(
            @RequestParam(value = "lessonId", defaultValue = "") UUID lessonId,
            @RequestParam(value = "rootId", required = false)UUID rootId,
            @RequestParam(value = SystemConstant.CURRENT_PAGE, required = false) Optional<Integer> currentPage,
            @RequestParam(value = SystemConstant.LIMIT_PAGE, required = false) Optional<Integer> limitPage
    ){
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(commentService.findCommentByLesson(lessonId, rootId, null, Scope.EXTERNAL, currentPage,
                        limitPage));
    }
}
