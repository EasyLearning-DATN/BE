package com.poly.easylearning.mapper;

import com.poly.easylearning.dto.ReactionDTO;
import com.poly.easylearning.dto.UserDTO;
import com.poly.easylearning.entity.Reaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ReactionMapper implements Function<Reaction, ReactionDTO> {
    private final UserMapper userMapper;
    @Override
    public ReactionDTO apply(Reaction reaction) {
        UserDTO creator = userMapper.apply(reaction.getUser());
        return ReactionDTO.builder()
                .liked(reaction.isLiked())
                .creator(creator)
                .commentID(reaction.getComment().getId())
                .build();
    }
}