package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.enums.Scope;
import com.poly.easylearning.payload.request.CommentRQ;
import com.poly.easylearning.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_COMMENT)
public class CommentMemberController {

    private final ICommentService commentService;
    @PostMapping
    public ResponseEntity<?> createComment(
            @AuthenticationPrincipal User user,
            @RequestBody CommentRQ commentRQ
    ){
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(commentService.create(commentRQ, user));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<?> deleteComment(
            @AuthenticationPrincipal User user,
            @PathVariable(SystemConstant.ID)UUID commentId
            ){
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(commentService.deleteComment(commentId, user));
    }
    @GetMapping
    public ResponseEntity<?> findByLesson(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "lessonId", defaultValue = "")UUID lessonId,
            @RequestParam(value = SystemConstant.CURRENT_PAGE, required = false) Optional<Integer> currentPage,
            @RequestParam(value = SystemConstant.LIMIT_PAGE, required = false) Optional<Integer> limitPage
    ){
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(commentService.findCommentByLesson(lessonId, user, Scope.MEMBER, currentPage, limitPage));
    }
}
