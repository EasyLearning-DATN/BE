package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.enums.Scope;
import com.poly.easylearning.payload.request.CommentStatusRQ;
import com.poly.easylearning.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_COMMENT)
public class CommentAdminController {
    private final ICommentService commentService;
    @PatchMapping(SystemConstant.API_CHANGE_STATUS)
    public ResponseEntity<?> changeStatus(
            @RequestBody CommentStatusRQ statusRQ
    ){
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(commentService.changeStatus(statusRQ));
    }

    @GetMapping
    public ResponseEntity<?> findByLesson(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "lessonId", defaultValue = "") UUID lessonId,
            @RequestParam(value = "rootId", required = false)UUID rootId,
            @RequestParam(value = SystemConstant.CURRENT_PAGE, required = false) Optional<Integer> currentPage,
            @RequestParam(value = SystemConstant.LIMIT_PAGE, required = false) Optional<Integer> limitPage
    ){
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(commentService.findCommentByLesson(lessonId, rootId, user, Scope.ADMIN, currentPage, limitPage));
    }
}
