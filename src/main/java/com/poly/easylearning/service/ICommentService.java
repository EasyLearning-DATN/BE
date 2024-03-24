package com.poly.easylearning.service;

import com.poly.easylearning.entity.Comment;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.enums.Scope;
import com.poly.easylearning.payload.request.CommentRQ;
import com.poly.easylearning.payload.request.CommentStatusRQ;
import com.poly.easylearning.payload.response.RestResponse;

import java.util.Optional;
import java.util.UUID;

public interface ICommentService {
    RestResponse create(CommentRQ commentRQ, User user);

    RestResponse deleteComment(UUID commentId, User user);

    RestResponse findCommentByLesson(UUID lessonId, UUID rootId, User user, Scope scope, Optional<Integer> currentPage
            , Optional<Integer> limitPage);

    RestResponse changeStatus(CommentStatusRQ statusRQ);

    Comment findCommentEntityById(UUID commentId);
}
