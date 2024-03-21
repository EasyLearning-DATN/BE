package com.poly.easylearning.mapper;

import com.poly.easylearning.dto.CommentDTO;
import com.poly.easylearning.dto.UserDTO;
import com.poly.easylearning.entity.Comment;
import com.poly.easylearning.entity.Reaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
@Component
@RequiredArgsConstructor
public class CommentMapper implements Function<Comment, CommentDTO> {
    private final UserMapper userMapper;

    @Override
    public CommentDTO apply(Comment comment) {
        UserDTO creator = userMapper.apply(comment.getUser());
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .creator(creator)
                .dateCreate(comment.getCreatedDate())
                .amountLike(getQuantityReaction(comment.getReactions(), true))
                .amountDislike(getQuantityReaction(comment.getReactions(), false))
                .build();
    }

    public CommentDTO applyForPublic(Comment comment) {
        UserDTO creator = userMapper.apply(comment.getUser());
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .creator(creator)
                .isCreator(false)
                .dateCreate(comment.getCreatedDate())
                .amountLike(getQuantityReaction(comment.getReactions(), true))
                .amountDislike(getQuantityReaction(comment.getReactions(), false))
                .build();
    }
    public CommentDTO applyForMember(Comment comment, boolean isCreator) {
        UserDTO creator = userMapper.apply(comment.getUser());
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .creator(creator)
                .isCreator(isCreator)
                .dateCreate(comment.getCreatedDate())
                .amountLike(getQuantityReaction(comment.getReactions(), true))
                .amountDislike(getQuantityReaction(comment.getReactions(), false))
                .build();
    }
    private int getQuantityReaction(List<Reaction> reactions, boolean isLiked){
        if(Objects.isNull(reactions)){
            return 0;
        }
        return (int) reactions.stream()
                .filter(reaction -> reaction.isLiked() == isLiked && !reaction.getIsDeleted())
                .count();
    }
}