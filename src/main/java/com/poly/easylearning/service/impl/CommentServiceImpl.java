package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.dto.CommentDTO;
import com.poly.easylearning.entity.Comment;
import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.enums.Scope;
import com.poly.easylearning.exception.ApiRequestException;
import com.poly.easylearning.mapper.CommentMapper;
import com.poly.easylearning.payload.request.CommentRQ;
import com.poly.easylearning.payload.request.CommentStatusRQ;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.ICommentRepo;
import com.poly.easylearning.service.ICommentService;
import com.poly.easylearning.service.ILessonService;
import com.poly.easylearning.service.IReactionService;
import com.poly.easylearning.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepo commentRepo;
    private final ILessonService lessonService;
    private final CommentMapper commentMapper;
    @Override
    public RestResponse create(CommentRQ commentRQ, User user) {
        Lesson lesson = lessonService.findLessonEntityById(commentRQ.getLessonId());
        Comment commentSaved = commentRepo.save(
                Comment.builder()
                        .content(commentRQ.getContent())
                        .usernameReply(commentRQ.getUsernameReply())
                        .user(user)
                        .lesson(lesson)
                        .enabled(true)
                        .isDeleted(false)
                        .rootId(commentRQ.getRootId())
                        .build()
        );
        return RestResponse.ok(ResourceBundleConstant.CMT_8001, commentMapper.apply(commentSaved));
    }

    @Override
    public RestResponse deleteComment(UUID commentId, User user) {
        Comment comment = findCommentEntityById(commentId);
        if(!user.getId().equals(comment.getUser().getId())){
            throw new ApiRequestException(ResourceBundleConstant.CMT_8003);
        }
        comment.setIsDeleted(true);
        commentRepo.save(comment);
        return RestResponse.ok(ResourceBundleConstant.CMT_8002, commentMapper.apply(comment));
    }

    @Override
    public RestResponse findCommentByLesson(UUID lessonId,UUID rootId, User user, Scope scope,
                                            Optional<Integer> currentPage, Optional<Integer> limitPage) {
        Pageable pageable = ResponseUtil.pageable(currentPage.orElse(1),
                limitPage.orElse(DefaultValueConstants.LIMIT_PAGE));
        Page<Comment> comments = null;
        List<CommentDTO> commentDTOS = new ArrayList<>();
        switch (scope) {
            case ADMIN -> {
                comments = commentRepo.findCommentByCondition(lessonId, rootId, pageable);
                commentDTOS = comments.stream()
                        .map(commentMapper).toList();
                break;
            }
            case MEMBER -> {
                UUID currentUserId = user.getId();
                comments = commentRepo.findPublicCommentByCondition(lessonId, rootId, pageable);
                commentDTOS = comments.stream()
                        .map(comment -> {
                            boolean isCreator = comment.getCreatedBy().equals(currentUserId);
                            return commentMapper.applyForMember(comment, isCreator);
                        }).toList();
                break;
            }
            case EXTERNAL -> {
                comments = commentRepo.findPublicCommentByCondition(lessonId, rootId, pageable);
                commentDTOS = comments.stream()
                        .map(commentMapper::applyForPublic).toList();
                break;
            }
        }

        ListResponse<CommentDTO> listResponse = ListResponse.build(comments.getTotalPages(), commentDTOS);
        return RestResponse.ok(ResourceBundleConstant.CMT_8005, listResponse);
    }

    @Override
    public RestResponse changeStatus(CommentStatusRQ statusRQ) {
        Comment comment = findCommentEntityById(statusRQ.getCommentId());
        comment.setEnabled(statusRQ.isEnable());
        Comment commentSaved = commentRepo.save(comment);
        return RestResponse.ok(ResourceBundleConstant.CMT_8006, commentMapper.apply(commentSaved));
    }

    @Override
    public Comment findCommentEntityById(UUID id) {
        return commentRepo.findById(id).orElseThrow(
                ()-> new ApiRequestException(ResourceBundleConstant.CMT_8004)
        );
    }
}
