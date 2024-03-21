package com.poly.easylearning.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
}
