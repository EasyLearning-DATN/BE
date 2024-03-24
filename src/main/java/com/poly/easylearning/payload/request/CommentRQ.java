package com.poly.easylearning.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRQ {
    private UUID rootId;
    private String content;
    private UUID lessonId;
    private String usernameReply;
}
