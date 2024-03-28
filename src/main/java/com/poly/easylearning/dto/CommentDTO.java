package com.poly.easylearning.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private UUID id;
    private String content;
    private LocalDateTime dateCreate;
    private UserDTO creator;
    private Boolean isCreator;
    private int amountLike;
    private int amountDislike;
    private UUID rootId;
    private int amountChild;
    private String usernameReply;
    private Boolean isLiked;
    private Boolean isDisLiked;
}
