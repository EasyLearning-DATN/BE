package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.Comment;
import com.poly.easylearning.entity.Reaction;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.mapper.ReactionMapper;
import com.poly.easylearning.payload.request.ReactionRQ;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IReactionRepo;
import com.poly.easylearning.service.ICommentService;
import com.poly.easylearning.service.IReactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReactionServiceImpl implements IReactionService {
    private final IReactionRepo reactionRepo;
    private final ReactionMapper reactionMapper;
    private final ICommentService commentService;

    @Override
    public RestResponse doReaction(User user, ReactionRQ reactionRQ) {
        Comment comment = commentService.findCommentEntityById(reactionRQ.getCommentId());
        if(Objects.nonNull(comment.getReactions())){
            Optional<Reaction> reaction = comment.getReactions().stream()
                    .filter(rec -> rec.isLiked() == reactionRQ.isLiked())
                    .findFirst();
            if(reaction.isPresent()){
                reaction.ifPresent(reactionRepo::delete);
                return RestResponse.ok(ResourceBundleConstant.RTC_9002, "OK");
            }
        }

        Reaction reactionSaved = reactionRepo.save(
                Reaction.builder()
                        .liked(reactionRQ.isLiked())
                        .user(user)
                        .comment(comment)
                        .build()
        );
        return RestResponse.ok(ResourceBundleConstant.RTC_9001, reactionMapper.apply(reactionSaved));
    }
}
