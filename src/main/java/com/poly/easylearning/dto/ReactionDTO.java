package com.poly.easylearning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ReactionDTO {
    private boolean liked;
    private UserDTO creator;
    private UUID commentID;
}
